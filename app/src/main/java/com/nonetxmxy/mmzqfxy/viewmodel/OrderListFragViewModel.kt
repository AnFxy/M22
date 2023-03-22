package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.OrderMessage
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OrderListFragViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _orders = MutableStateFlow<List<OrderMessage>>(emptyList())
    val orders: StateFlow<List<OrderMessage>> = _orders

    init {
        requestOrders()
    }

    private fun requestOrders() {
        launchUIWithDialog {
            _orders.value = orderRepository.getUserOrders(false)
        }
    }
}