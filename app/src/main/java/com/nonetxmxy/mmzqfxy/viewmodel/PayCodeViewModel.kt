package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.PayCodeMessage
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PayCodeViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _pagerData = MutableStateFlow<PayCodeMessage?>(null)
    val pagerData: StateFlow<PayCodeMessage?> = _pagerData

    fun getPayCodeData(payWayCode: String, mainId: Long, sonId: Long?, payType: Int) {
        launchUIWithDialog {
            _pagerData.value = orderRepository.getPayCodeMessageData(
                mainOrderId = mainId,
                sonOrderId = sonId,
                payWayId = payWayCode,
                payType = payType
            )
        }
    }
}
