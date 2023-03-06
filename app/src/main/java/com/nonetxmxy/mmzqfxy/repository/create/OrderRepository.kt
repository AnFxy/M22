package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.model.SampleOrder
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class OrderRepository @Inject constructor() : IOrderRepository {

    override suspend fun getOrders(): List<SampleOrder> {
        delay(1000)
        return listOf(
            SampleOrder(
                orderStatus = 0,
                loanAmount = 3000,
                periods = 2,
                cycle = 300,
                date = "3/4/2020",
                orderNum = 10000001,
                name = "名字1",
                days = 20
            ),
            SampleOrder(
                orderStatus = 1,
                loanAmount = 4000,
                periods = 1,
                cycle = 200,
                date = "3/4/2020",
                orderNum = 10000002,
                name = "名字2",
                days = 20
            ),
            SampleOrder(
                orderStatus = 2,
                loanAmount = 2000,
                periods = 2,
                cycle = 100,
                date = "3/4/2020",
                orderNum = 10000003,
                name = "名字3",
                days = 20
            ),
            SampleOrder(
                orderStatus = 3,
                loanAmount = 6000,
                periods = 2,
                cycle = 300,
                date = "8/4/2020",
                orderNum = 10000004,
                name = "名字4",
                days = 12
            ),
            SampleOrder(
                orderStatus = 3,
                loanAmount = 5000,
                periods = 2,
                cycle = 300,
                date = "9/4/2020",
                orderNum = 10000005,
                name = "名字5",
                days = -9
            )
        )
    }
}