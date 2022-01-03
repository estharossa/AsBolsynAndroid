package com.example.asbolsyn.order.data.repository

import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.order.data.service.OrderService
import com.example.asbolsyn.order.domain.repository.OrderRepository
import com.example.asbolsyn.utils.network.data.model.Result
import com.example.asbolsyn.utils.network.data.repository.BaseRepository


class OrderRepositoryImpl(
    private val service: OrderService
) : BaseRepository(), OrderRepository {

    override suspend fun getOrderList(): Result<List<OrderItemResponse>> = safeApiCall({ service.getOrders() })
    override suspend fun getOrderDetails(id: String): Result<OrderDetailsResponse> =
        safeApiCall({ service.getOrderDetails(id) })
}