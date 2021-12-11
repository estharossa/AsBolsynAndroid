package com.example.asbolsyn.utils.network

import com.example.asbolsyn.utils.network.data.model.NetworkConfig
import com.example.asbolsyn.utils.network.data.model.TokenStorage
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

class BaseNetworkConfig : NetworkConfig {

    override val baseURL = "https://testapi.io/api/ayauka777/"

    override val converterFactory = GsonConverterFactory.create(GsonBuilder().setLenient().create())

    override val defaultHeaders: HashMap<String, String> = hashMapOf()

    override val timeout = 120L

    override val tokenStorage: TokenStorage? = null
}