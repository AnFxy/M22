package com.nonetxmxy.mmzqfxy

import androidx.lifecycle.viewModelScope
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    // 为什么要缓存一个，因为shareflow没有粘性事件，当上游发送事件时，下游接受的已经销毁了，所以没有办法接收到
    // 除非下游在fragment实例的生命周期中启动协程进行监听，但这个又会有副作用，那就是fragment多次销毁重建都会新建监听者
    // 并且如果事件发送后，接收者接收到，但view销毁了，这个时候操作view就会出现空指针
    // 除非在onCreate方法中创建监听者
    // 如果缓存一个，那么当fragment重建后，就自然可以接收到缓存的事件
    // 如果fragment多次重建，那么就会多次消费
    // 所以需要记录已经被消费的事件
    private val _refreshPage = MutableSharedFlow<Long>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val refreshPage: SharedFlow<Long> = _refreshPage

    // 还款码页后让页面刷新
    fun sendRefreshEvent() {
        viewModelScope.launch {
            _refreshPage.emit(System.currentTimeMillis())
        }
    }
}