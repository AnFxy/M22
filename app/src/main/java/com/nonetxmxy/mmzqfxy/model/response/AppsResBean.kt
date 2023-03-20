package com.nonetxmxy.mmzqfxy.model.response

data class AppsResBean(
    val ymeF: List<AppBean>
)

data class AppBean(
    val CaktfKRiG: String, // app的名字
    val EJfFZ: String, // app logo的链接
    val KSB: Int, // App借款周期
    val cxRWCHNlYQA: String, // 分数
    val yPml: Int, // 最快审核天数
    val jrkS: Int, // 是否展示 1是 0否
    val XJUpUIdhWR: String, // 安卓下载链接
    val QjnEK: String, // 审核通过率
    val CbPtlpa: Double, // 最大借款金额
    val ChEDil: Double, // 最小借款金额
    val RoNFyXR: Double, // 到手金额
    val FepHBHuuj: Double, // 利率
    val gXNchYsf: Double, // 最大需要还款金额
    val mxOWjaFp: String, // app的编码
    val ufKPA: String, // 安卓的包名
)
