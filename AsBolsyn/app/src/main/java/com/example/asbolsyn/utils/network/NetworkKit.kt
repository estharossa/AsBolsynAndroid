package com.example.asbolsyn.utils.network

import com.example.asbolsyn.utils.network.data.model.NetworkConfig
import okhttp3.Interceptor

object NetworkKit {

    var apiClient = ApiClient()
    lateinit var configuration: NetworkConfig

    fun initWithConfig(config: NetworkConfig) {
        configuration = config
    }

    inline fun <reified S> createService(
        config: NetworkConfig = configuration,
        headers: HashMap<String, String>? = null,
        extraInterceptors: List<Interceptor> = emptyList()
    ) = apiClient.createApiService(
        config = config,
        serviceClass = S::class.java,
        additionalHeaders = headers,
        extraInterceptors = extraInterceptors
    )

}