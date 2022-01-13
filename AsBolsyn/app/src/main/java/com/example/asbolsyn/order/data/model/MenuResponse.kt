package com.example.asbolsyn.order.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @Expose @SerializedName("restaurant_id") val restaurantId: String,
    @Expose @SerializedName("restaurant_name") val restaurantName: String,
    @Expose @SerializedName("restaurant_info") val restaurantInfo: List<String>,
    @Expose @SerializedName("rating") val rating: String,
    @Expose val items: List<Item>
) {

    data class Item(
        @Expose val name: String,
        @Expose val description: String,
        @Expose val rating: String,
        @Expose val duration: Int,
        @Expose val price: Int
    )
}
