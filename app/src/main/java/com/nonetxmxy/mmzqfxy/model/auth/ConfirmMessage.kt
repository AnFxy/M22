package com.nonetxmxy.mmzqfxy.model.auth

data class ConfirmMessage(
    val requestAmount: Double,
    val requestDays: String,
    val proId: Long,
    val proName: String,
    val proLogo: String,
    val handAmount: Double,
    val rate: Double,
    val payDate: String,
    val needPayWhenDateOver: Double,
    val bankId: Long,
    val bankName: String,
    val bankNumber : String,
)