package com.example.asbolsyn.order.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderItemResponse(
    @Expose @SerializedName("restaurant_id") val restaurantId: String,
    @Expose @SerializedName("restaurant_name") val restaurantName: String,
    @Expose val date: String,
    @Expose val time: String,
    @Expose @SerializedName("order_number") val orderNumber: String,
    @Expose @SerializedName("is_active") val isActive: Boolean,
    @Expose @SerializedName("guests_number") val guests: Int
)
