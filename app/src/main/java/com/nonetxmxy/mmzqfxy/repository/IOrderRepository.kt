package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.ProductsBean
import com.nonetxmxy.mmzqfxy.model.SampleOrder
import com.nonetxmxy.mmzqfxy.model.response.AppBean
import com.nonetxmxy.mmzqfxy.model.response.MineInfo

interface IOrderRepository {

    suspend fun getOrders(): List<SampleOrder>

    suspend fun getProducts(): List<ProductsBean>

    suspend fun getAPPs(): List<AppBean>

    suspend fun getUserInfo(): MineInfo
}