package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.Regions
import com.nonetxmxy.mmzqfxy.model.auth.WorkMessage
import com.nonetxmxy.mmzqfxy.model.response.AllTags
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class AuthUserWorkViewModel @Inject constructor(
    private val beginRepository: IBeginRepository,
    private val authRepository: IAuthRepository,
    private val orderRepository: IOrderRepository
) : BaseViewModel() {
    private val _optionShowListFlow = MutableStateFlow<AllTags?>(null)
    val optionShowListFlow = _optionShowListFlow.asStateFlow()

    private val _administrativeListFlow = MutableStateFlow<Regions?>(null)
    val administrativeListFlow = _administrativeListFlow.asStateFlow()

    private val _pagerEventFlow = MutableSharedFlow<AuthPagerEvent>()
    val pagerEventFlow: SharedFlow<AuthPagerEvent> = _pagerEventFlow

    var startTime: Long = 0

    var pagerData = WorkMessage(
        zeLb = "",
        WLQ = "",
        TqqZwacSZ = "",
        PrKpqCuxQ = "",
        tLxEVr = "",
        Yrfwo = "",
        rIIWYi = "",
        Qnf = "",
        OsRQOT = "",
        rFAso = "",
        zuIMxSgT = "",
        CVZLaIndZG = "",
        VOpseRY = "",
        XHIcmEoky = "",
        ODZzYj = "",
        FVGLFJc = "",
        UpoeXeBjwVB = "",
        iueGnrcsZ = "",
        BRDyVcJsB = ""
    )

    init {
        startTime = System.currentTimeMillis()
        getPageData()
    }

    fun getPageData() {
        launchUIWithDialog {
            coroutineScope {
                joinAll(async {
                    _optionShowListFlow.emit(beginRepository.getOptionalDirections())
                }, async {
                    _administrativeListFlow.emit(beginRepository.getLatestRegions())
                })
                if (LocalCache.workCredit == 1) {
                    pagerData = authRepository.getSubmitWorkInfo()
                    _pagerEventFlow.emit(AuthPagerEvent.UpdatePageView)
                }
            }
            closeLoading.emit(Unit)
        }
    }

    fun submitWorkInfo() {
        launchUIWithDialog {
            authRepository.submitWorkInfo(pagerData, startTime)

            val mineInfo = orderRepository.getUserInfo()
            LocalCache.infoCredit = mineInfo.LbF.toInt()
            LocalCache.workCredit = mineInfo.UpolPGX.toInt()
            LocalCache.contactPersonCredit = mineInfo.NJO.toInt()
            LocalCache.idCredit = mineInfo.yDVrDaYTmXY.toInt()
            LocalCache.faceCredit = mineInfo.jFJE.toInt()
            LocalCache.bankCredit = mineInfo.ZxsKeqM.toInt()

            _pagerEventFlow.emit(AuthPagerEvent.GoNextPage)
        }
    }
}