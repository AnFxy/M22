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

    init {
        getPageData()
    }

    fun getPageData() {
        launchUIWithDialog {
            _payChannel.value = orderRepository.getPayWayMessageData()
        }
    }

    fun doConfirmExpanded(sonId: Long) {
        launchUIWithDialog {
            orderRepository.doConfirmExpand(sonId)
            _closeAndBackPage.emit(Unit)
        }
    }
}
