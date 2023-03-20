package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.response.UpdateResBean
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class SplashFragViewModel @Inject constructor(
    private val beginRepository: IBeginRepository
) : BaseViewModel() {

    val closePage = MutableSharedFlow<Unit>(replay = 1)

    val showTipsDialog = MutableSharedFlow<Unit>(replay = 1)

    val showUpdateDialog = MutableSharedFlow<UpdateResBean>(replay = 1)

    init {
        startLaunchFlow()
    }

    private fun startLaunchFlow() {
        launchUIWithDialog {
//            if (CheckFakePhoneUtil.isFacePhone()) {
//                closePage.emit(Unit)
//            } else {
            if (LocalCache.isShowedTips) {
                showUpdateDialog.emit(beginRepository.checkUpdateInformation())
            } else {
                showTipsDialog.emit(Unit)
            }
            //}
        }
    }

    fun checkUpdate() {
        launchUIWithDialog {
            showUpdateDialog.emit(beginRepository.checkUpdateInformation())
        }
    }
}