package com.example.asbolsyn.main.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class RestaurantsResponse(
    @Expose
    val restaurants: List<Item>
) {

    data class Item(
        @Expose
        @SerializedName("restaurant_name") val restaurantName: String,
        @Expose
        val category: String,
        @Expose
        @SerializedName("free_seats") val freeSeats: Int,
        @Expose
        val rate: String
    )
}
