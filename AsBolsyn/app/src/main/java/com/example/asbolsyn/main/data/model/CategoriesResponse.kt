package com.example.asbolsyn.main.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CategoriesResponse(
    @Expose val category: List<Item>
) {

    data class Item(
        @Expose @SerializedName("category_name") val categoryName: String,
        @Expose val img: String
    )
}
