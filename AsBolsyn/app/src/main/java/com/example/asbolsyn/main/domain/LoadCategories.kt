package com.example.asbolsyn.main.domain

import com.example.asbolsyn.main.data.model.CategoriesResponse
import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.utils.base.Either
import com.example.asbolsyn.utils.base.SuspendableUseCase
import com.example.asbolsyn.utils.network.data.model.Result

class LoadCategories(
    private val repository: RestaurantsRepository
) : SuspendableUseCase<Unit, CategoriesResponse> {

    override suspend fun run(params: Unit): Either<Throwable, CategoriesResponse> =
        when (val result = repository.getCategories()) {
            is Result.Success -> Either.Right(result.data)
            is Result.Error -> Either.Left(result.exception)
        }
}