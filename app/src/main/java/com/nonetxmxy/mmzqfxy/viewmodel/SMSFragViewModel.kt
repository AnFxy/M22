package com.nonetxmxy.mmzqfxy.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.LocationType
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.tools.GpsUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SMSFragViewModel @Inject constructor(
    private val beginRepository: IBeginRepository,
) : BaseViewModel() {

    val clock = MutableSharedFlow<Int>()

    val voiceClock = MutableSharedFlow<Int>()

    val goMain = MutableSharedFlow<Unit>()

    init {
        checkLastRequestTime()
        checkLastRequestVoiceTime()
    }

    private fun checkLastRequestTime() {
        val remainTime = (System.currentTimeMillis() - LocalCache.lastTimeSendCode) / 1000
        if (remainTime <= 60) {
            viewModelScope.launch {
                clock.emit(remainTime.toInt())
            }
        } else {
            smsCodeSent()
        }
    }

    private fun checkLastRequestVoiceTime() {
        val remainTime = (System.currentTimeMillis() - LocalCache.lastTimeSendVoiceCode) / 1000
        if (remainTime <= 60) {
            viewModelScope.launch {
                voiceClock.emit(remainTime.toInt())
            }
        }
    }

    fun smsCodeSent() {
        launchUIWithDialog {
            beginRepository.sendSmsCode()
            LocalCache.lastTimeSendCode = System.currentTimeMillis()
            clock.emit(60)
        }
    }

    fun voiceCodeSent() {
        launchUIWithDialog {
            beginRepository.sendVoiceCode()
            LocalCache.lastTimeSendVoiceCode = System.currentTimeMillis()
            voiceClock.emit(60)
        }
    }

    fun doLogin(context: Context, code: String) {
        launchUIWithDialog {
            // 上传定位埋点数据
            val loginBean = beginRepository.doLogin(code)
            LocalCache.token = loginBean.cLIuElaqh
            LocalCache.isSAccount = loginBean.qTzzWfJh == 1
            GpsUtil(context).location?.let {
                LocalCache.lonLocal = it.longitude.toString()
                LocalCache.latiLocal = it.latitude.toString()
                beginRepository.submitLocationData(LocationType.LOGIN)
            }
            LocalCache.isLogged = true
            goMain.emit(Unit)
        }
    }
}