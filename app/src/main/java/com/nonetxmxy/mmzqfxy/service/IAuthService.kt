package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.model.BaseResponse
import com.nonetxmxy.mmzqfxy.model.auth.*
import com.nonetxmxy.mmzqfxy.model.response.BankListResBean
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    // 获取个人认证信息
    @POST(NetPaths.verifiGot)
    suspend fun userMessageGot(@Body maps: HashMap<String, String>): BaseResponse<UserMessage>

    // 获取工作认证信息
    @POST(NetPaths.verifiGot)
    suspend fun workMessageGot(@Body maps: HashMap<String, String>): BaseResponse<WorkMessage>

    // 获取联系人认证信息
    @POST(NetPaths.verifiGot)
    suspend fun contractMessageGot(@Body maps: HashMap<String, String>): BaseResponse<ContractMessage>

    // 获取身份证认证信息
    @POST(NetPaths.verifiGot)
    suspend fun idMessageGot(@Body maps: HashMap<String, String>): BaseResponse<IDMessage>

    // 获取银行卡认证信息
    @POST(NetPaths.verifiGot)
    suspend fun cardMessageGot(@Body maps: HashMap<String, String>): BaseResponse<BankListResBean>

    // 提交个人认证信息
    @POST(NetPaths.verifiSubmit)
    suspend fun userMessageSent(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 提交工作认证信息
    @POST(NetPaths.verifiSubmit)
    suspend fun workMessageSend(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 提交联系人认证信息
    @POST(NetPaths.verifiSubmit)
    suspend fun contractMessageSent(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 提交身份证认证信息
    @POST(NetPaths.verifiSubmit)
    suspend fun idMessageSent(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 提交银行卡信息
    @POST(NetPaths.verifiSubmit)
    suspend fun cardMessageSent(@Body maps: HashMap<String, String>): BaseResponse<BankMessage>


    // 调用OCR
    @POST(NetPaths.verifiSubmit)
    suspend fun ocrSent(@Body maps: HashMap<String, String>): BaseResponse<OCRResponse>

    // 提交人脸识别
    @POST(NetPaths.verifiSubmit)
    suspend fun faceSent(@Body maps: HashMap<String, String>): BaseResponse<Unit>
}