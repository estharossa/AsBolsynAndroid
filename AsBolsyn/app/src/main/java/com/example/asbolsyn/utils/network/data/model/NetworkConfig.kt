package com.example.asbolsyn.utils.network.data.model

import retrofit2.converter.gson.GsonConverterFactory

interface NetworkConfig {
    val baseURL: String
    val timeout: Long
    val tokenStorage: TokenStorage?
    val converterFactory: GsonConverterFactory?
    val defaultHeaders: HashMap<String, String>?
}
