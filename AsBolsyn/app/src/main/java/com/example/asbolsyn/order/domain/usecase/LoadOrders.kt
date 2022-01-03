package com.example.asbolsyn.order.domain.usecase

import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.order.domain.repository.OrderRepository
import com.example.asbolsyn.utils.network.data.model.Either
import com.example.asbolsyn.utils.network.data.model.SuspendableUseCase
import com.example.asbolsyn.utils.network.data.model.Result

class LoadOrders(
    private val repository: OrderRepository
) : SuspendableUseCase<Unit, List<OrderItemResponse>> {

    override suspend fun run(params: Unit): Either<Throwable, List<OrderItemResponse>> =
        when (val result = repository.getOrderList()) {
            is Result.Success -> Either.Right(result.data)
            is Result.Error -> Either.Left(result.exception)
        }
}