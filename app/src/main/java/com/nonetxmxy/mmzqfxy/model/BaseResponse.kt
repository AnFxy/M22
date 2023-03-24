package com.nonetxmxy.mmzqfxy.model

import com.blankj.utilcode.util.ActivityUtils
import com.nonetxmxy.mmzqfxy.base.LocalCache

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
        if (code == 101) {
            // 清除缓存，退出app
            LocalCache.clearALLCache()
            ActivityUtils.finishAllActivities()
        } else {
            if (code != 1) {
                throw Exception("$message")
            }
        }
    }
}
