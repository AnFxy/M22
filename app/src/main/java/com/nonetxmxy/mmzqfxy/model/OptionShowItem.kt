package com.nonetxmxy.mmzqfxy.model

import androidx.annotation.Keep

@Keep
data class OptionShowList(
    val marryStatus: List<OptionShowItem>?)

@Keep
data class OptionShowItem(val dataValue: String, val showContent: String)

