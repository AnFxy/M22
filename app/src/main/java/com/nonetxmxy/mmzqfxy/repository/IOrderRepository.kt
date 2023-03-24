package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.*
import com.nonetxmxy.mmzqfxy.model.auth.ConfirmMessage
import com.nonetxmxy.mmzqfxy.model.response.AppBean
import com.nonetxmxy.mmzqfxy.model.response.ConfirmResBean
import com.nonetxmxy.mmzqfxy.model.response.MineInfo

interface IOrderRepository {
    suspend fun getUserOrders(isReviewing: Boolean): List<OrderMessage>

    suspend fun getProducts(): List<ProductsBean>

    suspend fun getAPPs(isReviewing: Boolean): List<AppBean>

    suspend fun getUserInfo(): MineInfo

    suspend fun getRequestConfirmData(): ConfirmResBean

    suspend fun submitRequestConfirm(confirmMessage: ConfirmMessage)

    suspend fun getRepayData(): RepayMessage

    suspend fun getPayWayMessageData(): List<PayWayMessage>

    suspend fun getPayCodeMessageData(
        mainOrderId: Long,
        sonOrderId: Long?,
        payWayId: String,
        payType: Int
    ): PayCodeMessage

    suspend fun doConfirmExpand(sonOrderId: Long)
}