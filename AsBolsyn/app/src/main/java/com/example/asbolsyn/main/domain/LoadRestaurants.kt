package com.example.asbolsyn.main.domain

import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.utils.base.Either
import com.example.asbolsyn.utils.base.SuspendableUseCase
import com.example.asbolsyn.utils.network.data.model.Result

class LoadRestaurants(
    private val repository: RestaurantsRepository
) : SuspendableUseCase<Unit, RestaurantsResponse> {

    override suspend fun run(params: Unit): Either<Throwable, RestaurantsResponse> =
        when (val result = repository.getRestaurants()) {
            is Result.Success -> Either.Right(result.data)
            is Result.Error -> Either.Left(result.exception)
        }
}