package com.example.asbolsyn.order.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderDetailsResponse(
    @Expose @SerializedName("restaurant_id") val restaurantId: String,
    @Expose @SerializedName("restaurant_name") val restaurantName: String,
    @Expose val date: String,
    @Expose val time: String,
    @Expose @SerializedName("order_number") val orderNumber: String,
    @Expose @SerializedName("is_active") val isActive: Boolean,
    @Expose @SerializedName("guests_number") val guestsNumber: Int,
    @Expose @SerializedName("pricing") val pricingList: List<Pricing>,
    @Expose val guests: List<Guest>,
    @Expose @SerializedName("room_number") val roomNumber: String
) {

    data class Pricing(
        @Expose val item: String,
        @Expose val count: Int,
        @Expose val price: Int
    )

    data class Guest(
        @Expose val name: String,
        @Expose @SerializedName("pricing") val pricingList: List<Pricing>
    )
}
