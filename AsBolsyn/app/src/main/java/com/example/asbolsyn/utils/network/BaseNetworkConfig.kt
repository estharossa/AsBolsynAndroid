package com.example.asbolsyn.utils.network

import com.example.asbolsyn.utils.CommonConstants
import com.example.asbolsyn.utils.network.data.model.NetworkConfig
import com.example.asbolsyn.utils.network.data.model.TokenStorage
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

class BaseNetworkConfig : NetworkConfig {

    override val baseURL = CommonConstants.API_BASE_URL

    override val converterFactory: GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setLenient().create())

    override val defaultHeaders: HashMap<String, String> = hashMapOf()

    override val timeout = 120L

    override val tokenStorage: TokenStorage? = null
}