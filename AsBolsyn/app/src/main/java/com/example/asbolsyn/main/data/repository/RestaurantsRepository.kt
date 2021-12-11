package com.example.asbolsyn.main.data

import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.main.data.service.RestaurantsService
import com.example.asbolsyn.main.domain.RestaurantsRepository
import com.example.asbolsyn.utils.network.data.model.Result
import com.example.asbolsyn.utils.network.data.repository.BaseRepository


class RestaurantsRepositoryImpl(
    private val service: RestaurantsService
) : BaseRepository(), RestaurantsRepository {

    companion object {

    }

    override suspend fun getRestaurants(): Result<RestaurantsResponse> = safeApiCall({ service.getRestaurants() })
}