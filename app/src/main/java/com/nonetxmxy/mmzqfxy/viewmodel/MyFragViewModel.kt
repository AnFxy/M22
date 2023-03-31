package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.OrderMessage
import com.nonetxmxy.mmzqfxy.model.RepayMessage
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyFragViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _orders = MutableStateFlow<List<OrderMessage>>(emptyList())
    val orders: StateFlow<List<OrderMessage>> = _orders

    private val _currentItem = MutableStateFlow<OrderMessage?>(null)
    val currentItem: StateFlow<OrderMessage?> = _currentItem

    init {
        getPageData()
    }

    fun getPageData() {
       launchUIWithDialog {
           _orders.value = orderRepository.getUserOrders(false)
           // 找出需要还款的
           val needPayItems = orders.value.filter { item -> item.ozdniMVY in listOf(0, 2)  }
           if (needPayItems.isNotEmpty()) {
               // 找出剩余天数最小的
               val needQuickPayItem = needPayItems.minBy { item -> item.JMgRrdrv }
               _currentItem.value = needQuickPayItem
           } else {
               _currentItem.value = null
           }
           closeLoading.emit(Unit)
       }
    }
}
