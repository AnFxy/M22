package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.SampleOrder

interface IOrderRepository {

    suspend fun getOrders() : List<SampleOrder>
}