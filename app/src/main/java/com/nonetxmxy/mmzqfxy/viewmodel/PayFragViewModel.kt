package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.PayWayMessage
import com.nonetxmxy.mmzqfxy.model.RepayMessage
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class PayFragViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _pagerData = MutableStateFlow<RepayMessage?>(null)
    val pagerData: StateFlow<RepayMessage?> = _pagerData

    private val _payChannel = MutableStateFlow<List<PayWayMessage>>(emptyList())
    val payChannel: StateFlow<List<PayWayMessage>> = _payChannel

    val needUpdate = MutableSharedFlow<Unit>()

    var sonId: Long? = null
    var payType: Int = 1

    init {
        getPageData()
    }

    fun getPageData() {
        launchUIWithDialog {
            coroutineScope {
                joinAll(
                    async {
                        _pagerData.value = orderRepository.getRepayData()
                    },
                    async {
                        _payChannel.value = orderRepository.getPayWayMessageData()
                    }
                )
            }
            closeLoading.emit(Unit)
        }
    }

    fun selectCode() {
        launchUIWithDialog {
            val firstRecommend = payChannel.value.filter { it.KKEMXfGmlVt == 1 }
            if (firstRecommend.isNotEmpty()) {
                // 查询还款码
                val result = orderRepository.getPayCodeMessageData(
                    mainOrderId = pagerData.value?.oqiuffK?.get(0)?.kcUBu ?: 0,
                    sonOrderId = sonId,
                    payWayId = firstRecommend[0].jBsB,
                    payType = payType
                )
                _payChannel.value.first { it.KKEMXfGmlVt == 1 }.code = result.QWZRFcNqe
                needUpdate.emit(Unit)
            }
        }
    }
}