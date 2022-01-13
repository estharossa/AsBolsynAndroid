package com.example.asbolsyn.order.domain.usecase

import com.example.asbolsyn.order.data.model.MenuResponse
import com.example.asbolsyn.order.domain.repository.OrderRepository
import com.example.asbolsyn.utils.network.data.model.Either
import com.example.asbolsyn.utils.network.data.model.SuspendableUseCase
import com.example.asbolsyn.utils.network.data.model.Result

class LoadRestaurantMenu(
    private val repository: OrderRepository
) : SuspendableUseCase<String, MenuResponse> {

    override suspend fun run(params: String): Either<Throwable, MenuResponse> =
        when (val result = repository.getRestaurantMenu(params)) {
            is Result.Success -> Either.Right(result.data)
            is Result.Error -> Either.Left(result.exception)
        }
}