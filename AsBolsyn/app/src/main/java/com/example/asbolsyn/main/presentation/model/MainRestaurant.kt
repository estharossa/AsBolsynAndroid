package com.example.asbolsyn.main.presentation.model

import androidx.annotation.DrawableRes

data class MainRestaurant(
    val title: String,
    val category: String,
    val freeSeatsNumber: Int,
    val rate: Double,
    @DrawableRes val backgroundImage: Int
)
