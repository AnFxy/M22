package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.PayWayMessage
import com.nonetxmxy.mmzqfxy.model.RepayMessage
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
}