package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.model.BaseResponse
import com.nonetxmxy.mmzqfxy.model.auth.UserMessage
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    // 获取个人认证信息
    @POST(NetPaths.verifiGot)
    suspend fun userMessageGot(@Body maps: HashMap<String, String>): BaseResponse<UserMessage>
}