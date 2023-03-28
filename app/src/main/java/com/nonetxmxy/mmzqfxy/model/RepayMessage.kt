package com.nonetxmxy.mmzqfxy.model

import java.io.Serializable

data class RepayMessage(
    val OEdZXUBY: Double, // 需要还款的总金额
    val XYz: Int, // 需要还款的总期数
    val Djb: Int, // 是否可再借
    val oqiuffK: List<OrderPeriodMessage>
)

class OrderPeriodMessage(
    val BANcfnGeXv: String?, // 产品项目编码
    val kcUBu: Long?, // 所属父订单的id
    val tTeY: Int?, // 订单状态
    val cyJ: Double?, // 父订单借款金额
    val eJwh: Long?, //  本期订单ID
    val QhNNScsTT: Double?, // 本期剩余还要还的钱
    val jgCtnsRuLhN: Double?, // 本期初始要还的钱
    val CZusa: Int?, // 本期的还款状态
    val eQEBh: String?, // 本期还款日期
    val VHXe: Double?, // 本期的利息
    val PXsQKJs: Int?, // 本期的序号
    val BHWg: Int?, // 是否展示展期按钮
    val fLk: Int?, // 本期剩余还款天数
    val sZOWLJOMQ: Double?, // 本期展期的费用
    val nMq: Int?, // 本期可以展期多少天
    val WJgjPYFKSKZ: Double?, // 本期已经还款金额
    val PHBPS: String?, // 展期后的截至还款日期
    val ccnN: Double?, // 展期后应还金额
    val fxhOFanoPe: Int?, // 展期页面是否可以点击确认展期按钮
    val eSu: String, // 产品的logo
    val IAtgc: String, // 产品的名称
    val CqEjtx: String?, // 借款周期
    val rejC: Int?, // 总期数
    val tcwHIg: Double?, // 优惠金额
    val HAkD: Int?, // 是否可单独还款
) : Serializable {
    var isChecked = false
}
