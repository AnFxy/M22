package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.base.net.RetrofitSet

class ServiceImpl {
    companion object {
        fun giveIBeginService(): IBeginService =
            RetrofitSet.onlyOne.provideRetrofit().create(IBeginService::class.java)

        fun giveIUserAuthService(): IAuthService =
            RetrofitSet.onlyOne.provideRetrofit().create(IAuthService::class.java)
    }
}
