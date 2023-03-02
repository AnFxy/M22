package com.nonetxmxy.mmzqfxy.viewmodel

import androidx.lifecycle.viewModelScope
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.DialogSet
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.repository.create.BeginRepository
import com.nonetxmxy.mmzqfxy.tools.CheckFakePhoneUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashFragViewModel @Inject constructor(
    private val beginRepository: BeginRepository
) : BaseViewModel() {

    val closePage = MutableSharedFlow<Unit>()

    val showTipsDialog = MutableSharedFlow<Unit>()

    init {
       startLaunchFlow()
    }

    private fun startLaunchFlow() {
        viewModelScope.launch {
            if (CheckFakePhoneUtil.isFacePhone()) {
                closePage.emit(Unit)
            } else {
                if (LocalCache.isShowTips) {
                    showTipsDialog.emit(Unit)
                } else {

                }
            }
        }
    }

}