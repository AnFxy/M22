package com.nonetxmxy.mmzqfxy.viewmodel

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.Market
import android.content.Context
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.LocationType
import com.nonetxmxy.mmzqfxy.model.PhotoType
import com.nonetxmxy.mmzqfxy.model.auth.IDMessage
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import com.nonetxmxy.mmzqfxy.tools.GpsUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class AuthIdentityViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val orderRepository: IOrderRepository,
    private val beginRepository: IBeginRepository
) : BaseViewModel() {

    private val _pagerEventFlow = MutableSharedFlow<AuthPagerEvent>()
    val pagerEventFlow: SharedFlow<AuthPagerEvent> = _pagerEventFlow

    val photoType = MutableStateFlow(PhotoType.TOP_CAME)

    private var startTime: Long = 0

    private var faceTime: Long = 0

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

    private val _setFacePic = MutableSharedFlow<Unit>()
    val setFacePic: SharedFlow<Unit> = _setFacePic

    private val _startFace = MutableSharedFlow<Unit>()
    val startFace: SharedFlow<Unit> = _startFace

    private val _prepareFaceOkay = MutableSharedFlow<Unit>()
    val prepareFaceOkay: SharedFlow<Unit> = _prepareFaceOkay

    init {
        startTime = System.currentTimeMillis()
        getIdentifyPageData()
    }

    fun getIdentifyPageData() {
        launchUIWithDialog {
            if (LocalCache.idCredit == 1) {
                pagerData = authRepository.getSubmitIDCardInfo()
                _pagerEventFlow.emit(AuthPagerEvent.UpdatePageView)
            }
            closeLoading.emit(Unit)
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

    fun updateFaceTime() {
        faceTime = System.currentTimeMillis()
    }

    fun getLocationWhenFace(context: Context) {
        launchUIWithDialog {
            GpsUtil(context).location?.let {
                LocalCache.lonLocal = it.longitude.toString()
                LocalCache.latiLocal = it.latitude.toString()
                beginRepository.submitLocationData(LocationType.FACE)
            }
            _prepareFaceOkay.emit(Unit)
        }
    }

    fun submitFaceWithCamera() {
        launchUIWithDialog {
            authRepository.submitFaceInfo(faceTime)

            val mineInfo = orderRepository.getUserInfo()
            LocalCache.infoCredit = mineInfo.LbF.toInt()
            LocalCache.workCredit = mineInfo.UpolPGX.toInt()
            LocalCache.contactPersonCredit = mineInfo.NJO.toInt()
            LocalCache.idCredit = mineInfo.yDVrDaYTmXY.toInt()
            LocalCache.faceCredit = mineInfo.jFJE.toInt()
            LocalCache.bankCredit = mineInfo.ZxsKeqM.toInt()

            _setFacePic.emit(Unit)
        }
    }

    fun getFaceConfig() {
        launchUIWithDialog {
            val faceConfig = beginRepository.getFaceConfig().xAVbNF
            LocalCache.faceAccessKey = faceConfig.paTbpP
            LocalCache.faceSecretKey = faceConfig.zZwHc
            GuardianLivenessDetectionSDK.init(
                Utils.getApp(),
                faceConfig.paTbpP,
                faceConfig.zZwHc,
                Market.Mexico
            )

            _startFace.emit(Unit)
        }
    }

    fun uploadFacePic(picStr: String) {
        launchUIWithDialog {
            val fileUpResult = authRepository.uploadFile(picStr, "jpg").checkDataEmpty()
            LocalCache.facePhoto = fileUpResult.url
            authRepository.submitFaceInfo(faceTime)

            val mineInfo = orderRepository.getUserInfo()
            LocalCache.infoCredit = mineInfo.LbF.toInt()
            LocalCache.workCredit = mineInfo.UpolPGX.toInt()
            LocalCache.contactPersonCredit = mineInfo.NJO.toInt()
            LocalCache.idCredit = mineInfo.yDVrDaYTmXY.toInt()
            LocalCache.faceCredit = mineInfo.jFJE.toInt()
            LocalCache.bankCredit = mineInfo.ZxsKeqM.toInt()

            _setFacePic.emit(Unit)
        }
    }
}