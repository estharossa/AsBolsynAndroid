package com.example.asbolsyn.order.domain.usecase

import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.domain.repository.OrderRepository
import com.example.asbolsyn.utils.network.data.model.Either
import com.example.asbolsyn.utils.network.data.model.SuspendableUseCase
import com.example.asbolsyn.utils.network.data.model.Result

class LoadOrderDetails(
    private val repository: OrderRepository
) : SuspendableUseCase<String, OrderDetailsResponse> {

    override suspend fun run(params: String): Either<Throwable, OrderDetailsResponse> =
        when (val result = repository.getOrderDetails(params)) {
            is Result.Success -> Either.Right(result.data)
            is Result.Error -> Either.Left(result.exception)
        }
}