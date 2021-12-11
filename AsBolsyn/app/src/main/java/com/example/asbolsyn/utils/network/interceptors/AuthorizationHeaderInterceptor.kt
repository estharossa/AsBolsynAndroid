package com.example.asbolsyn.utils.network.interceptors

import com.example.asbolsyn.utils.network.data.model.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthorizationHeaderInterceptor(private val tokenStorage: TokenStorage?): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if (!tokenStorage?.token.isNullOrEmpty()) {
            return chain.proceed(original.newBuilder()
                .addHeader("Authorization", "Bearer ${tokenStorage?.token}")
                .method(original.method, original.body).build())
        }

        return chain.proceed(original.newBuilder().build())
    }
}