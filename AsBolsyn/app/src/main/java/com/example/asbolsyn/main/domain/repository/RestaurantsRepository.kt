package com.example.asbolsyn.main.domain.repository

import com.example.asbolsyn.main.data.model.CategoriesResponse
import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.utils.network.data.model.Result

interface RestaurantsRepository {
    suspend fun getRestaurants(): Result<RestaurantsResponse>
    suspend fun getCategories(): Result<CategoriesResponse>
}