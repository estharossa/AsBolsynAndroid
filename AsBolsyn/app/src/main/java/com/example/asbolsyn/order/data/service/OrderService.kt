package com.example.asbolsyn.order.data.service

import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.data.model.OrderItemResponse
import retrofit2.http.*

interface OrderService {

    @GET("orders")
    suspend fun getOrders(): List<OrderItemResponse>

    @GET("orders/{id}")
    suspend fun getOrderDetails(@Path("id") id: String): OrderDetailsResponse
}