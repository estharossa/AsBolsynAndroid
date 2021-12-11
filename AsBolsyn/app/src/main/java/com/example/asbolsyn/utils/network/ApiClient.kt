package com.example.asbolsyn.utils.network

import com.example.asbolsyn.utils.network.data.model.NetworkConfig
import com.example.asbolsyn.utils.network.interceptors.AuthorizationHeaderInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.asbolsyn.utils.network.interceptors.HeaderInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    fun <S> createApiService(
        config: NetworkConfig,
        serviceClass: Class<S>,
        additionalHeaders: HashMap<String, String>? = null,
        extraInterceptors: List<Interceptor> = emptyList()
    ): S {
        val interceptors = listOf(
            AuthorizationHeaderInterceptor(config.tokenStorage),
            HeaderInterceptor(config.defaultHeaders),
            HeaderInterceptor(additionalHeaders),
        ) + extraInterceptors

        return Retrofit.Builder()
            .baseUrl(config.baseURL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(config.converterFactory ?: GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(config.timeout, TimeUnit.SECONDS)
                    .readTimeout(config.timeout, TimeUnit.SECONDS)
                    .writeTimeout(config.timeout, TimeUnit.SECONDS)
                    .apply {
                        interceptors.forEach { interceptor ->
                            addInterceptor(interceptor)
                        }
                    }
                    .build()
            )
            .build()
            .create(serviceClass)
    }

}
