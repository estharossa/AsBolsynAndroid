package com.example.asbolsyn.order.domain.repository

import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.utils.network.data.model.Result

interface OrderRepository {
    suspend fun getOrderList(): Result<List<OrderItemResponse>>
}