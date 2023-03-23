package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.PhotoType
import com.nonetxmxy.mmzqfxy.model.auth.IDMessage
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class AuthIdentityViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _pagerEventFlow = MutableSharedFlow<AuthPagerEvent>()
    val pagerEventFlow: SharedFlow<AuthPagerEvent> = _pagerEventFlow

    val photoType = MutableStateFlow<PhotoType>(PhotoType.TOP_CAME)

    var startTime: Long = 0

    var faceTime: Long = 0

    var pagerData = IDMessage(
        AgzmxkTVhrb = "",
        fOEEzcNxpv = "",
        aNFqORfCZpt = "",
        ZdKyAmeCGxL = "",
        ExHTUA = "",
        DEyLxCETnd = "",
        GKPoxepe = "",
        MpOHLbXEBT = "",
        JkfImZtlQ = "",
        Tjq = ""
    )

    init {
        startTime = System.currentTimeMillis()
        getIdentifyPageData()
    }

    private fun getIdentifyPageData() {
        if (LocalCache.idCredit == 1) {
            launchUIWithDialog {
                pagerData = authRepository.getSubmitIDCardInfo()
                _pagerEventFlow.emit(AuthPagerEvent.UpdatePageView)
            }
        }
    }

    fun startOCRFlow(photoUrl: String, flag: Int) {

        launchUIWithDialog {
            if (flag == 1) {
                val ocrResult = authRepository.doOCRFlow(photoUrl)
                if (ocrResult.RFxTW?.isNotEmpty() == true) {
                    pagerData = pagerData.copy(ExHTUA = ocrResult.RFxTW)
                }
                if (ocrResult.UsQp?.isNotEmpty() == true) {
                    pagerData = pagerData.copy(fOEEzcNxpv = ocrResult.UsQp)
                }
                if (ocrResult.cslvRKltzMO?.isNotEmpty() == true) {
                    pagerData = pagerData.copy(Tjq = ocrResult.cslvRKltzMO)
                }
                if (ocrResult.iwJZ?.isNotEmpty() == true) {
                    pagerData = pagerData.copy(JkfImZtlQ = ocrResult.iwJZ)
                }
                if (ocrResult.qWDZQ?.isNotEmpty() == true) {
                    pagerData = pagerData.copy(ZdKyAmeCGxL = ocrResult.qWDZQ)
                }
                pagerData = pagerData.copy(AgzmxkTVhrb = photoUrl)
            } else {
                pagerData = pagerData.copy(MpOHLbXEBT = photoUrl)
            }

            _pagerEventFlow.emit(AuthPagerEvent.UpdatePageView)
        }
    }

    fun submitIdentifyInfo() {
        launchUIWithDialog {
            authRepository.submitIDCardInfo(pagerData, startTime)

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