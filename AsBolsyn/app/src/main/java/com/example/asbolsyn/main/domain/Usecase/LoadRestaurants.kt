package com.example.asbolsyn.main.domain.Usecase

import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.main.domain.Repository.RestaurantsRepository
import com.example.asbolsyn.utils.network.data.model.Either
import com.example.asbolsyn.utils.network.data.model.SuspendableUseCase
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