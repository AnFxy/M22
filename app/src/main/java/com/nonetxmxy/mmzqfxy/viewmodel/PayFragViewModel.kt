package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.RepayMessage
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PayFragViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _pagerData = MutableStateFlow<RepayMessage?>(null)
    val pagerData: StateFlow<RepayMessage?> = _pagerData

    init {
        getPageData()
    }

    fun getPageData() {
        launchUIWithDialog {
            _pagerData.value = orderRepository.getRepayData()
        }
    }
}