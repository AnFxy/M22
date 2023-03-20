package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.model.BaseResponse
import com.nonetxmxy.mmzqfxy.model.ConfigBean
import com.nonetxmxy.mmzqfxy.model.LoginBean
import com.nonetxmxy.mmzqfxy.model.response.BanksResBean
import com.nonetxmxy.mmzqfxy.model.response.UpdateResBean
import com.nonetxmxy.mmzqfxy.model.response.UseTypeResBean
import retrofit2.http.Body
import retrofit2.http.POST

interface IBeginService {

    // 获取配置
    @POST(NetPaths.configGot)
    suspend fun configGot(@Body map: HashMap<String, String>): BaseResponse<ConfigBean>

    // 检查更新
    @POST(NetPaths.checkAPPUpdate)
    suspend fun update(@Body map: HashMap<String, String>): BaseResponse<UpdateResBean>

    // 获取用户类型
    @POST(NetPaths.userType)
    suspend fun userType(@Body map: HashMap<String, String>): BaseResponse<UseTypeResBean>

    // 发送Sms验证码
    @POST(NetPaths.smsCode)
    suspend fun smsCode(@Body map: HashMap<String, String>): BaseResponse<Unit>

    // 发送语音验证码
    @POST(NetPaths.voiceCode)
    suspend fun voiceCode(@Body map: HashMap<String, String>): BaseResponse<Unit>

    // 登录
    @POST(NetPaths.login)
    suspend fun login(@Body map: HashMap<String, String>): BaseResponse<LoginBean>

    // 登出
    @POST(NetPaths.logout)
    suspend fun logout(@Body map: HashMap<String, String>): BaseResponse<Unit>

    // 获取所有银行名字
    @POST(NetPaths.banks)
    suspend fun banksGot(@Body map: HashMap<String, String>): BaseResponse<BanksResBean>
}