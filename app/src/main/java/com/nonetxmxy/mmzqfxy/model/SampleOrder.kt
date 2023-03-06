package com.nonetxmxy.mmzqfxy.model

data class SampleOrder(
    val orderStatus: Int,
    val loanAmount: Int,
    val periods: Int,
    val cycle: Int,
    val date: String,
    val orderNum: Long,
    val name: String,
    val days: Int
)
