package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.PayWayMessage
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExpandFragViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _payChannel = MutableStateFlow<List<PayWayMessage>>(emptyList())
    val payChannel: StateFlow<List<PayWayMessage>> = _payChannel

    private val _closeAndBackPage = MutableSharedFlow<Unit>()
    val closeAndBackPage: SharedFlow<Unit> = _closeAndBackPage

    val needUpdate = MutableSharedFlow<Unit>()

    init {
        getPageData()
    }

    fun getPageData() {
        launchUIWithDialog {
            _payChannel.value = orderRepository.getPayWayMessageData()
        }
    }

    fun selectCode(mainId: Long, sonId: Long) {
        launchUIWithDialog {
            val firstRecommend = payChannel.value.filter { it.KKEMXfGmlVt == 1 }
            if (firstRecommend.isNotEmpty()) {
                // 查询还款码
                val result = orderRepository.getPayCodeMessageData(
                    mainOrderId = mainId,
                    sonOrderId = sonId,
                    payWayId = firstRecommend[0].jBsB,
                    payType = 2
                )
                _payChannel.value.first { it.KKEMXfGmlVt == 1 }.code = result.QWZRFcNqe
                needUpdate.emit(Unit)
            }
        }
    }

    fun doConfirmExpanded(sonId: Long) {
        launchUIWithDialog {
            orderRepository.doConfirmExpand(sonId)
            _closeAndBackPage.emit(Unit)
        }
    }
}
