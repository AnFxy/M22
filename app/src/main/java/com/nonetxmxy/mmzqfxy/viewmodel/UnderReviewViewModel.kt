package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.OrderMessage
import com.nonetxmxy.mmzqfxy.model.ProductsBean
import com.nonetxmxy.mmzqfxy.model.response.AppBean
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class UnderReviewViewModel @Inject constructor(
    val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _proData = MutableStateFlow<OrderMessage?>(null)
    val proData: StateFlow<OrderMessage?> = _proData

    private val _appsData = MutableStateFlow<List<AppBean>>(emptyList())
    val appsData: StateFlow<List<AppBean>> = _appsData

    init {
        getPageData()
    }

    fun getPageData() {
        launchUIWithDialog {
            coroutineScope {
                joinAll(
                    async {
                        _proData.value =
                            orderRepository.getUserOrders(true)[0]
                    },
                    async {
                        _appsData.value = orderRepository.getAPPs()
                    }
                )
            }
        }
    }
}
