package com.example.asbolsyn.main.data.repository

import com.example.asbolsyn.main.data.model.CategoriesResponse
import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.main.data.service.RestaurantsService
import com.example.asbolsyn.main.domain.Repository.RestaurantsRepository
import com.example.asbolsyn.utils.network.data.model.Result
import com.example.asbolsyn.utils.network.data.repository.BaseRepository


class RestaurantsRepositoryImpl(
    private val service: RestaurantsService
) : BaseRepository(), RestaurantsRepository {

    override suspend fun getRestaurants(): Result<RestaurantsResponse> =
        safeApiCall({ service.getRestaurants() })

    override suspend fun getCategories(): Result<CategoriesResponse> =
        safeApiCall({ service.getCategories() })
}