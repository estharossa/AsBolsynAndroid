package com.example.asbolsyn.main.data.service

import com.example.asbolsyn.main.data.model.CategoriesResponse
import com.example.asbolsyn.main.data.model.RestaurantsResponse
import retrofit2.http.*

interface RestaurantsService {

    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantsResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse
}