package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.auth.ContractMessage
import com.nonetxmxy.mmzqfxy.model.response.AllTags
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AuthContactPersonViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val beginRepository: IBeginRepository,
    private val orderRepository: IOrderRepository
) :
    BaseViewModel() {

    private val _optionShowListFlow = MutableStateFlow<AllTags?>(null)
    val optionShowListFlow = _optionShowListFlow.asStateFlow()

    private val _pagerEventFlow = MutableSharedFlow<AuthPagerEvent>()
    val pagerEventFlow: SharedFlow<AuthPagerEvent> = _pagerEventFlow

    private val _pagerDataFlow = MutableStateFlow(
        ContractMessage(
            Cqr = "",
            RHaDS = "",
            KiVk = "",
            pRgj = "",
            faVW = "",
            vwuan = "",
            tvNbOHA = "",
            gSNfDy = ""
        )
    )
    val pagerDataFlow: StateFlow<ContractMessage> = _pagerDataFlow

    private var startTime: Long = 0L

    init {
        startTime = System.currentTimeMillis()
        getPageData()
    }

    private fun getPageData() {
        launchUIWithDialog {
            _optionShowListFlow.emit(beginRepository.getOptionalDirections())
            if (LocalCache.contactPersonCredit == 1) {
                _pagerDataFlow.emit(authRepository.getSubmitContractInfo())
            }
        }
    }

    fun submitContractPerson() {
        launchUIWithDialog {
            authRepository.submitContractInfo(pagerDataFlow.value, startTime)

            val oldStatus = LocalCache.contactPersonCredit == 1

            val mineInfo = orderRepository.getUserInfo()
            LocalCache.infoCredit = mineInfo.LbF.toInt()
            LocalCache.workCredit = mineInfo.UpolPGX.toInt()
            LocalCache.contactPersonCredit = mineInfo.NJO.toInt()
            LocalCache.idCredit = mineInfo.yDVrDaYTmXY.toInt()
            LocalCache.faceCredit = mineInfo.jFJE.toInt()
            LocalCache.bankCredit = mineInfo.ZxsKeqM.toInt()

            if (oldStatus) {
                _pagerEventFlow.emit(AuthPagerEvent.Finish)
            } else {
                _pagerEventFlow.emit(AuthPagerEvent.GoNextPage)
            }
        }
    }

    fun updateContactData(selectContact1: Boolean, phone: String, name: String) {
        if (selectContact1) {
            if (phone != pagerDataFlow.value.faVW) {
                _pagerDataFlow.value = pagerDataFlow.value.copy(RHaDS = phone, KiVk = name)
            }
        } else {
            if (phone != pagerDataFlow.value.RHaDS) {
                _pagerDataFlow.value = pagerDataFlow.value.copy(faVW = phone, vwuan = name)
            }
        }
    }

    fun updateContactRelationShip(
        isFirst: Boolean = true,
        relationShipId: String,
        relationShip: String
    ) {
        if (isFirst) {
            _pagerDataFlow.value =
                pagerDataFlow.value.copy(Cqr = relationShipId, tvNbOHA = relationShip)
        } else {
            _pagerDataFlow.value =
                pagerDataFlow.value.copy(pRgj = relationShipId, gSNfDy = relationShip)
        }
    }
}
