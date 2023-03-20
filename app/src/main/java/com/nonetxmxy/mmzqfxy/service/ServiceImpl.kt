package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.base.net.RetrofitSet

class ServiceImpl {
    companion object {
        fun giveIBeginService(): IBeginService =
            RetrofitSet.onlyOne.provideRetrofit().create(IBeginService::class.java)

        fun giveIAuthService(): IAuthService =
            RetrofitSet.onlyOne.provideRetrofit().create(IAuthService::class.java)

        fun giveIMainService(): IMainService =
            RetrofitSet.onlyOne.provideRetrofit().create(IMainService::class.java)

        fun giveIFileUploadService(): IFileUploadService =
            RetrofitSet.onlyOne.provideRetrofit(BuildConfig.FILE_URL)
                .create(IFileUploadService::class.java)
    }
}
