package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.model.*
import com.nonetxmxy.mmzqfxy.model.response.*
import retrofit2.http.Body
import retrofit2.http.POST

interface IMainService {
    // 首页产品列表
    @POST(NetPaths.productLists)
    suspend fun productsList(@Body maps: HashMap<String, String>): BaseResponse<ProductResBean>

    // 获取用户所有认证状态
    @POST(NetPaths.allVerifiMessage)
    suspend fun allVerifiMessage(@Body maps: HashMap<String, String>): BaseResponse<AllVerifiMessageResBean>

    // 获取推荐APP列表
    @POST(NetPaths.apps)
    suspend fun appsRecommend(@Body maps: HashMap<String, String>): BaseResponse<AppsResBean>

    // 提交用户点击推荐APP
    @POST(NetPaths.clickAppsRecord)
    suspend fun clickAppRecord(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 获取行政规划区域
    @POST(NetPaths.regions)
    suspend fun regionArea(@Body maps: HashMap<String, String>): BaseResponse<Regions>

    // 获取字典值
    @POST(NetPaths.optionShowList)
    suspend fun getTags(@Body maps: HashMap<String, String>): BaseResponse<AllTags>

    // 提交客服意见反馈
    @POST(NetPaths.feedBack)
    suspend fun submitFeedBack(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 提交三大埋点数据
    @POST(NetPaths.threeHugeHidden)
    suspend fun submitThreeHugeHidden(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 获取历史订单列表
    @POST(NetPaths.orderList)
    suspend fun orderListGot(@Body maps: HashMap<String, String>): BaseResponse<OrderResBean>

    // 获取还款页面数据
    @POST(NetPaths.repayList)
    suspend fun repayListGot(@Body maps: HashMap<String, String>): BaseResponse<RepayMessage>

    // 获取 还款渠道
    @POST(NetPaths.repayWay)
    suspend fun repayWayGot(@Body maps: HashMap<String, String>): BaseResponse<List<PayWayMessage>>

    // 生成还款码
    @POST(NetPaths.repayCode)
    suspend fun repayCodeGot(@Body maps: HashMap<String, String>): BaseResponse<PayCodeMessage>

    // 点击确认展期
    @POST(NetPaths.confirmExpand)
    suspend fun doConfirmExpand(@Body maps: HashMap<String, String>): BaseResponse<Unit>

    // 上传定位的埋点数据
    @POST(NetPaths.hiddenData)
    suspend fun submitHiddenData(@Body maps: HashMap<String, String>): BaseResponse<Unit>
}