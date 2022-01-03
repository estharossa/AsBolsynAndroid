package com.example.asbolsyn.order.data.service

import com.example.asbolsyn.order.data.model.OrderItemResponse
import retrofit2.http.*

interface OrderService {

    @GET("orders")
    suspend fun getOrders(): List<OrderItemResponse>
}