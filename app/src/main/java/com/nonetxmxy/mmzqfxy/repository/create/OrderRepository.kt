package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.*
import com.nonetxmxy.mmzqfxy.model.auth.ConfirmMessage
import com.nonetxmxy.mmzqfxy.model.response.AppBean
import com.nonetxmxy.mmzqfxy.model.response.ConfirmResBean
import com.nonetxmxy.mmzqfxy.model.response.MineInfo
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import com.nonetxmxy.mmzqfxy.service.IAuthService
import com.nonetxmxy.mmzqfxy.service.IMainService
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val mainService: IMainService,
    private val authService: IAuthService
) : IOrderRepository {

    override suspend fun getUserOrders(isReviewing: Boolean): List<OrderMessage> {
        val maps = HashMap<String, String>()
        maps["CPlyMkkOk"] = BuildConfig.CODE
        maps["JMSdO"] = BuildConfig.LANGUAGE
        if (isReviewing) {
            maps["cVTli"] = LocalCache.currentProCode
        }
        maps["wLY"] = LocalCache.token
        maps["ZBTDVJ"] = "4"
        return mainService.orderListGot(maps).checkDataEmpty().koPbk
    }


    override suspend fun getProducts(): List<ProductsBean> {
        val maps = HashMap<String, String>()
        maps["uVx"] = BuildConfig.CODE
        maps["ZAUqTIK"] = BuildConfig.LANGUAGE
        if (LocalCache.token.isNotEmpty()) {
            maps["zdzVswbBoC"] = LocalCache.token
        }
        return mainService.productsList(maps).checkDataEmpty().soNLPDaZSdh
    }

    override suspend fun getAPPs(isUnderReview: Boolean): List<AppBean> {
        val maps = HashMap<String, String>()
        maps["ElH"] = BuildConfig.CODE
        maps["bMQUAlD"] = BuildConfig.LANGUAGE
        maps["emrccuSydv"] = LocalCache.token
        maps["mIdVQD"] = LocalCache.currentProCode
        maps["SzcwfTdRKEP"] = if (isUnderReview) "2" else "1"
        return mainService.appsRecommend(maps).checkDataEmpty().ymeF
    }

    override suspend fun getUserInfo(): MineInfo {
        val maps = HashMap<String, String>()
        maps["iYWu"] = BuildConfig.CODE
        maps["eFVWQmuScjk"] = BuildConfig.LANGUAGE
        maps["LCQw"] = LocalCache.token
        maps["PYpiJ"] = LocalCache.currentProCode
        return mainService.allVerifiMessage(maps).checkDataEmpty().xmYDzadg
    }

    override suspend fun getRequestConfirmData(): ConfirmResBean {
        val maps = HashMap<String, String>()
        maps["tStrjExUrlW"] = BuildConfig.CODE
        maps["ZYeJqBJraD"] = BuildConfig.LANGUAGE
        maps["YtKlh"] = LocalCache.currentProCode
        maps["ndAgEfgc"] = LocalCache.token

        return authService.loanConfirmGot(maps).checkDataEmpty()
//        return ConfirmResBean(
//            SsxAXO = listOf(
//                ConfirmMainItem(
//                    tRNwi = 1,
//                    xAGMGmRcGRX = "产品1",
//                    cvsE = "",
//                    qUSFV = "45", // 借款周期
//                    hUkdTOMt = 2, // 总期数
//                    zrCuab = 2000.0, // 借款金额
//                    Eng = 1800.0, // 到手金额
//                    rqNdvWKSU = 1, //分期类型，1平均分期，2不规则分期
//                    eEn = listOf(
//                        ConfirmPeroidItem(
//                            qScch = 1, // 第几期
//                            MxMb = 1100.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 40, // 还款天数
//                        ),
//                        ConfirmPeroidItem(
//                            qScch = 2, // 第几期
//                            MxMb = 1100.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 5, // 还款天数
//                        )
//                    )
//                ),
//                ConfirmMainItem(
//                    tRNwi = 2,
//                    xAGMGmRcGRX = "产品2",
//                    cvsE = "",
//                    qUSFV = "20", // 借款周期
//                    hUkdTOMt = 2, // 总期数
//                    zrCuab = 2000.0, // 借款金额
//                    Eng = 1800.0, // 到手金额
//                    rqNdvWKSU = 1, //分期类型，1平均分期，2不规则分期
//                    eEn = listOf(
//                        ConfirmPeroidItem(
//                            qScch = 1, // 第几期
//                            MxMb = 1100.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 10, // 还款天数
//                        ),
//                        ConfirmPeroidItem(
//                            qScch = 2, // 第几期
//                            MxMb = 1100.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 10, // 还款天数
//                        )
//                    )
//                ),
//                ConfirmMainItem(
//                    tRNwi = 3,
//                    xAGMGmRcGRX = "产品3",
//                    cvsE = "",
//                    qUSFV = "7", // 借款周期
//                    hUkdTOMt = 2, // 总期数
//                    zrCuab = 2000.0, // 借款金额
//                    Eng = 1800.0, // 到手金额
//                    rqNdvWKSU = 1, //分期类型，1平均分期，2不规则分期
//                    eEn = listOf(
//                        ConfirmPeroidItem(
//                            qScch = 1, // 第几期
//                            MxMb = 1100.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 4, // 还款天数
//                        ),
//                        ConfirmPeroidItem(
//                            qScch = 2, // 第几期
//                            MxMb = 1100.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 3, // 还款天数
//                        )
//                    )
//                ),
//                ConfirmMainItem(
//                    tRNwi = 4,
//                    xAGMGmRcGRX = "产品4",
//                    cvsE = "",
//                    qUSFV = "20", // 借款周期
//                    hUkdTOMt = 2, // 总期数
//                    zrCuab = 1000.0, // 借款金额
//                    Eng = 800.0, // 到手金额
//                    rqNdvWKSU = 1, //分期类型，1平均分期，2不规则分期
//                    eEn = listOf(
//                        ConfirmPeroidItem(
//                            qScch = 1, // 第几期
//                            MxMb = 600.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 10, // 还款天数
//                        ),
//                        ConfirmPeroidItem(
//                            qScch = 2, // 第几期
//                            MxMb = 600.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 10, // 还款天数
//                        )
//                    )
//                ),
//                ConfirmMainItem(
//                    tRNwi = 5,
//                    xAGMGmRcGRX = "产品5",
//                    cvsE = "",
//                    qUSFV = "30", // 借款周期
//                    hUkdTOMt = 2, // 总期数
//                    zrCuab = 1000.0, // 借款金额
//                    Eng = 800.0, // 到手金额
//                    rqNdvWKSU = 1, //分期类型，1平均分期，2不规则分期
//                    eEn = listOf(
//                        ConfirmPeroidItem(
//                            qScch = 1, // 第几期
//                            MxMb = 650.0, // 当前期数需要还款的金额
//                            DqCiL = 150.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 15, // 还款天数
//                        ),
//                        ConfirmPeroidItem(
//                            qScch = 2, // 第几期
//                            MxMb = 550.0, // 当前期数需要还款的金额
//                            DqCiL = 50.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 15, // 还款天数
//                        )
//                    )
//                ),
//                ConfirmMainItem(
//                    tRNwi = 6,
//                    xAGMGmRcGRX = "产品6",
//                    cvsE = "",
//                    qUSFV = "10", // 借款周期
//                    hUkdTOMt = 2, // 总期数
//                    zrCuab = 600.0, // 借款金额
//                    Eng = 400.0, // 到手金额
//                    rqNdvWKSU = 1, //分期类型，1平均分期，2不规则分期
//                    eEn = listOf(
//                        ConfirmPeroidItem(
//                            qScch = 1, // 第几期
//                            MxMb = 400.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 5, // 还款天数
//                        ),
//                        ConfirmPeroidItem(
//                            qScch = 2, // 第几期
//                            MxMb = 400.0, // 当前期数需要还款的金额
//                            DqCiL = 100.0, // 利息
//                            jWQxkCqnvxd = System.currentTimeMillis().toString(), // 计划还款日期，时间戳
//                            gMxodl = 5, // 还款天数
//                        )
//                    )
//                )
//            ),
//            OxnxByLr = ConfirmBankContent(
//                WgHXYqpyQ = 1, // 银行卡的id
//                HlgpsFlSUTF = "银行1", // 银行名称
//                IlI = "345234523452345234", // 卡号
//                CdUyOLniE = "", // 持卡人
//                DUNV = "3", // 银行卡类型
//                aApfbWh = "CLABE", // 银行卡类型显示字段
//            )
//        )
    }

    override suspend fun submitRequestConfirm(confirmMessage: ConfirmMessage) {
        val maps = HashMap<String, String>()
        maps["fqLVIvK"] = BuildConfig.CODE
        maps["Pvfks"] = BuildConfig.LANGUAGE
        maps["UqrV"] = LocalCache.currentProCode
        maps["LwIQNNlFHXu"] = LocalCache.token
        maps["ZZPY"] = confirmMessage.bankId.toString()
        maps["fgRkNuvqGBn"] = confirmMessage.proId.toString()

        authService.applicationRequest(maps).checkCodeError()
    }

    override suspend fun getRepayData(): RepayMessage {
        val maps = HashMap<String, String>()
        maps["MPjhAyLvY"] = BuildConfig.CODE
        maps["tgAPI"] = BuildConfig.LANGUAGE
        maps["wWxfSHoRbo"] = LocalCache.currentProCode
        maps["GFjpYB"] = LocalCache.token
        maps["vqqb"] = "4"
        return mainService.repayListGot(maps).checkDataEmpty()
    }

    override suspend fun getPayWayMessageData(): List<PayWayMessage> {
        val maps = HashMap<String, String>()
        maps["VisfHICVX"] = BuildConfig.CODE
        maps["zlYrkRY"] = BuildConfig.LANGUAGE
        maps["DwfXq"] = LocalCache.token
        maps["JtbEgyLEmU"] = LocalCache.currentProCode
        return mainService.repayWayGot(maps).checkDataEmpty()
    }

    override suspend fun getPayCodeMessageData(
        mainOrderId: Long,
        sonOrderId: Long?,
        payWayId: String,
        payType: Int
    ): PayCodeMessage {
        val maps = HashMap<String, String>()
        maps["FxQtrIWDjl"] = BuildConfig.CODE
        maps["DBjP"] = BuildConfig.LANGUAGE
        maps["sBAFCuKlnd"] = LocalCache.token
        maps["bfjEM"] = mainOrderId.toString()
        maps["xHhzYlPMX"] = payWayId
        maps["gGs"] = LocalCache.currentProCode
        maps["drYvC"] = payType.toString()
        sonOrderId?.let {
            if (payType != 5) {
                maps["jxRvqQ"] = it.toString()
            }
        }
        return mainService.repayCodeGot(maps).checkDataEmpty()
    }

    override suspend fun doConfirmExpand(sonOrderId: Long) {
        val maps = HashMap<String, String>()
        maps["BqPiiCuy"] = BuildConfig.CODE
        maps["kuAaWN"] = BuildConfig.LANGUAGE
        maps["flzYnIRqQ"] = LocalCache.token
        maps["tDgvW"] = sonOrderId.toString()

        mainService.doConfirmExpand(maps).checkCodeError()
    }
}