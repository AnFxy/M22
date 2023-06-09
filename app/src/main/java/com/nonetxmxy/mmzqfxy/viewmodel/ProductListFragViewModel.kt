package com.nonetxmxy.mmzqfxy.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.ProductsBean
import com.nonetxmxy.mmzqfxy.model.response.AppBean
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import com.nonetxmxy.mmzqfxy.view.ProductListFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductListFragViewModel @Inject constructor(
    val beginRepository: IBeginRepository,
    val orderRepository: IOrderRepository
) : BaseViewModel() {

    val products = MutableStateFlow<List<ProductsBean>>(emptyList())
    val apps = MutableStateFlow<List<AppBean>>(emptyList())

    val navDirections = MutableSharedFlow<NavDirections>()

    init {
        getConfig()
    }

    fun getConfig() {
        launchUIWithDialog {
            coroutineScope {
                joinAll(
                    async {
                        val configBean = beginRepository.getAppConfig()
                        LocalCache.serviceNumber = configBean.vMSCnEiPym ?: ""
                        LocalCache.email = configBean.Fbi ?: ""
                    },
                    async {
                        val products = orderRepository.getProducts()
                        LocalCache.currentProCode = products[0].ANddPfvNno
                        this@ProductListFragViewModel.products.value = products
                        if (LocalCache.isLogged) {
                            val mineInfo = orderRepository.getUserInfo()
                            LocalCache.infoCredit = mineInfo.LbF.toInt()
                            LocalCache.workCredit = mineInfo.UpolPGX.toInt()
                            LocalCache.contactPersonCredit = mineInfo.NJO.toInt()
                            LocalCache.idCredit = mineInfo.yDVrDaYTmXY.toInt()
                            LocalCache.faceCredit = mineInfo.jFJE.toInt()
                            LocalCache.bankCredit = mineInfo.ZxsKeqM.toInt()
                        }
                    },
                    async {
                        if (LocalCache.isLogged) {
                            val apps = orderRepository.getAPPs(false)
                            this@ProductListFragViewModel.apps.value = apps
                        }
                    }
                )
            }
            closeLoading.emit(Unit)
        }
    }

    fun startNextPageFlow(productBean: ProductsBean) {
        if (productBean.ZnXsyPioYz == 0) return

        viewModelScope.launch {
            when (productBean.jMdbPgc) {
                // 去借款流程
                0, 1, 5, 6 -> navDirections.emit(ProductListFragmentDirections.actionProductListFragmentToAuthNavigation())
                // 审核状态流程
                2, 3, 4 -> {
                    navDirections.emit(ProductListFragmentDirections.actionProductListFragmentToUnderReviewFragment())
                }
                // 还款流程
                7, 8 -> {
                    navDirections.emit(ProductListFragmentDirections.actionProductListFragmentToPayNavigation())
                }
                else -> {
                    // 不做任何处理
                }
            }
        }
    }
}
