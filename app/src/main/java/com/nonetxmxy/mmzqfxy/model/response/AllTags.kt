package com.nonetxmxy.mmzqfxy.model.response

data class AllTags(
    val JDnuGMCy: List<Tags>, // 婚姻状态
    val dYHUukCW: List<Tags>, // 月收入
    val PBjZodK: List<Tags>, // 教育水平等级
    val cSmAoN: List<Tags>, // 婚姻状况
    val ihdXBrF: List<Tags>, // 银行卡类型
    val zHezuLsDK: List<Tags>, // 联系人关系
    val BiJe: List<Tags>, // 反馈类型
    val UdohNUQyLNR: List<Tags>, // 订单状态
    val WcUub: List<Tags>, // 工作类型
    val GrvYN: List<Tags>, // 宗教类型
    val pAf: List<Tags>, // 孩子个数
    val BlrvIUr: List<Tags>, // 工作支付日
    val sCdzUti: List<Tags>, // 工作类型
    val pVevX: List<Tags>, // 工作支付频率
    val GYUgF: List<Tags>, // 收入来源
)

data class Tags(
    val mplqN: String, // dataValue
    val rNRxtQyarFI: String, // showContent
)
