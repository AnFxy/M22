package com.nonetxmxy.mmzqfxy.model.response

data class UpdateResBean(
    val uvFD: String,// 下载链接
    val oMGtrc: Int,// app是否最新版本
    val ITwm: Int, // 是否需要更新1是0否
    val eHuxdiAPuTS: String // 更新提示的文案
)
