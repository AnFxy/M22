package com.nonetxmxy.mmzqfxy.model


data class OptionShowList(
    val marryStatus: List<OptionShowItem>?)

data class OptionShowItem(val dataValue: String, val showContent: String)

