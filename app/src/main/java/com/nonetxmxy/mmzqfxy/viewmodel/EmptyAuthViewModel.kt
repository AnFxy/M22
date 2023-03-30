package com.nonetxmxy.mmzqfxy.viewmodel

import androidx.lifecycle.viewModelScope
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmptyAuthViewModel @Inject constructor(
    private val repository: IOrderRepository
) : BaseViewModel() {

    val finishPage = MutableSharedFlow<Unit>()

    init {
        getPagerData()
    }

    private fun getPagerData() {
        launchUIWithDialog ({
            val mineInfo = repository.getUserInfo()
            LocalCache.infoCredit = mineInfo.LbF.toInt()
            LocalCache.workCredit = mineInfo.UpolPGX.toInt()
            LocalCache.contactPersonCredit = mineInfo.NJO.toInt()
            LocalCache.idCredit = mineInfo.yDVrDaYTmXY.toInt()
            LocalCache.faceCredit = mineInfo.jFJE.toInt()
            LocalCache.bankCredit = mineInfo.ZxsKeqM.toInt()

            checkGoWhichVerificationPage()
        }, {
            finishPage.emit(Unit)
        })
    }
}
