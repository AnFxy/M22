package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.SampleOrder
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OrderListFragViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    val orders = MutableStateFlow<List<SampleOrder>>(emptyList())

    init {
        requestOrders()
    }

    private fun requestOrders() {
        launchUIWithDialog {
            orders.value = orderRepository.getOrders()
        }
    }
}