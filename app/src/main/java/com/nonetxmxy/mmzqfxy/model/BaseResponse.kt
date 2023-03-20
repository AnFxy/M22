package com.nonetxmxy.mmzqfxy.model

data class BaseResponse<T>(
    val data: T? = null,
    val code: Int? = null,
    val message: String? = null,
) {
    fun checkDataEmpty(): T {
        checkCodeError()
        return data ?: throw Exception("Data Empty!")
    }

    fun checkCodeError() {
        if (code != 1) {
            throw Exception("$message")
        }
    }
}
