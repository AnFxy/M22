package com.nonetxmxy.mmzqfxy.model

import androidx.annotation.Keep

@Keep
data class SelfData(
    val educationLevelShow: String,//show content
    val educationLevel: String,//submit parameter
    val marryStatusShow: String,
    val marryStatus: String,
    val childrenTotalShow: String,
    val childrenTotal: String,
    val familyProvince: String,
    val familyCity: String,
    val familyAddress: String
)

@Keep
data class WorkData(
    val workNature: String,
    val workNatureShow: String,
    val incomeSourceType: String,
    val incomeSourceTypeShow: String,
    val companyMonthIncome: String,
    val companyMonthIncomeShow: String,
    //这里的选项不是必填项
/*    val payCycle: String?,
    val payCycleShow: String?,
    val payday: String?,
    val paydayShow: String?,
    val beginWorkYear: String?,
    val companyName: String?,
    val companyProvince: String?,
    val companyCity: String?,
    val companyAddress: String?,
    val companyPhone: String?*/
)
