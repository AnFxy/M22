package com.nonetxmxy.mmzqfxy.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.tools.ErrorHandleUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val loadingEvent = MutableSharedFlow<Boolean>()
    val _loadingEvent: SharedFlow<Boolean> = loadingEvent

    // 关闭加载圈
    protected val closeLoading = MutableSharedFlow<Unit>(replay = 1)
    val _closeLoading: SharedFlow<Unit> = closeLoading

    protected val baseGoPage = MutableSharedFlow<PageType>()
    val _baseGoPage: SharedFlow<PageType> = baseGoPage

    //运行在UI线程的协程
    fun launchUIWithDialog(
        block: suspend CoroutineScope.() -> Unit,
        onError: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch {
        try {
            // loading事件只在BaseActivity中消费
            loadingEvent.emit(true)
            block()
            loadingEvent.emit(false)
        } catch (e: Exception) {
            ErrorHandleUtil.handleError(e)
            loadingEvent.emit(false)
            closeLoading.emit(Unit)
            onError()
        }
    }

    fun launchUIWithDialog(block: suspend CoroutineScope.() -> Unit) = launchUIWithDialog(block, {})

    // 没有加载框，可自定义启动时，出错时，结束时 UI需要做的事情
    fun launchUI(
        block: suspend CoroutineScope.() -> Unit,
        onStart: (() -> Unit?)? = null,
        onError: ((e: Exception) -> Unit?)? = null,
        onFinished: (() -> Unit?)? = null,
    ) = viewModelScope.launch {
        try {
            onStart?.invoke()
            block()
        } catch (e: Exception) {
            onError?.invoke(e)
            ErrorHandleUtil.handleError(e)
        } finally {
            onFinished?.invoke()
        }
    }

    fun checkGoWhichVerificationPage() {
        viewModelScope.launch {
            baseGoPage.emit(
                if (LocalCache.workCredit == 0) {
                    PageType.WORK
                } else if (LocalCache.infoCredit == 0) {
                    PageType.USER
                } else if (LocalCache.contactPersonCredit == 0) {
                    PageType.CONTRACT
                } else if (LocalCache.idCredit == 0) {
                    PageType.ID
                }
//                else if (LocalCache.faceCredit == 0) {
//                    PageType.FACE
//                }
                else if (LocalCache.bankCredit == 0) {
                    PageType.BANK
                } else {
                    PageType.CONFIRM
                }
            )
        }
    }
}
