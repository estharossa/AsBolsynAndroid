package com.example.asbolsyn.main.domain

import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.utils.network.data.model.Result

interface RestaurantsRepository {
    suspend fun getRestaurants(): Result<RestaurantsResponse>
}