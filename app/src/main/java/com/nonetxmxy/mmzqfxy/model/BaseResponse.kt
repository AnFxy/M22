package com.nonetxmxy.mmzqfxy.model

data class BaseResponse<T>(
    val data: T? = null,
    val code: Int? = null,
    private val message: String? = null,
)
