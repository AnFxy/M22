package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.auth.BankMessage
import com.nonetxmxy.mmzqfxy.model.response.AllTags
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import com.nonetxmxy.mmzqfxy.repository.create.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class AddCardsFragViewModel @Inject constructor(
    private val beginRepository: IBeginRepository,
    private val authRepository: AuthRepository,
    private val orderRepository: IOrderRepository
) : BaseViewModel() {

    private val _banks = MutableStateFlow<List<String>>(emptyList())
    val banks: StateFlow<List<String>> = _banks

    private val _optionalDirections = MutableStateFlow<AllTags?>(null)
    val optionalDirection: StateFlow<AllTags?> = _optionalDirections

    private val _goNextPage = MutableSharedFlow<Unit>()
    val goNextPage: SharedFlow<Unit> = _goNextPage

    var pagerData = BankMessage(
        ZlE = "",
        TtoUz = "",
        zUbbNgrgLl = "",
        XpuVfIvsAt = "",
        RhgBNBzglD = "",
        YfpxWBMDrp = "",
    )

    var startTime: Long = 0L

    init {
        startTime = System.currentTimeMillis()
        getPageData()
    }

    private fun getPageData() {
        launchUIWithDialog {
            joinAll(
                async {
                    _banks.value = beginRepository.getLatestBanks()
                },
                async {
                    _optionalDirections.value = beginRepository.getOptionalDirections()
                }
            )
        }
    }

    fun submitBank() {
        launchUIWithDialog {
            authRepository.submitBanks(pagerData, startTime)

            val mineInfo = orderRepository.getUserInfo()
            LocalCache.infoCredit = mineInfo.LbF.toInt()
            LocalCache.workCredit = mineInfo.UpolPGX.toInt()
            LocalCache.contactPersonCredit = mineInfo.NJO.toInt()
            LocalCache.idCredit = mineInfo.yDVrDaYTmXY.toInt()
            LocalCache.faceCredit = mineInfo.jFJE.toInt()
            LocalCache.bankCredit = mineInfo.ZxsKeqM.toInt()

            _goNextPage.emit(Unit)
        }
    }
}
