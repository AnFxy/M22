package com.nonetxmxy.mmzqfxy.model.response

data class ConfirmResBean(
    val SsxAXO: List<ConfirmMainItem>,
    val OxnxByLr: ConfirmBankContent,
)

data class ConfirmMainItem(
    val tRNwi: Long, // 产品ID
    val xAGMGmRcGRX: String, // 产品名称
    val cvsE: String?, //  产品logo
    val qUSFV: String, // 借款周期
    val hUkdTOMt: Int, // 总期数
    val zrCuab: Double, // 借款金额
    val Eng: Double, // 到手金额
    val rqNdvWKSU: Int, //分期类型，1平均分期，2不规则分期
    val eEn: List<ConfirmPeroidItem>
)

data class ConfirmPeroidItem(
    val qScch: Int, // 第几期
    val MxMb: Double, // 当前期数需要还款的金额
    val DqCiL: Double, // 利息
    val jWQxkCqnvxd: String, // 计划还款日期，时间戳
    val gMxodl: Int, // 还款天数
)

data class ConfirmBankContent(
    val WgHXYqpyQ: Long, // 银行卡的id
    val HlgpsFlSUTF: String, // 银行名称
    val IlI: String, // 卡号
    val CdUyOLniE: String, // 持卡人
    val DUNV: String, // 银行卡类型
    val aApfbWh: String, // 银行卡类型显示字段
)
