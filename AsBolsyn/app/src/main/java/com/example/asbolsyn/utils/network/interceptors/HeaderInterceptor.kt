package com.example.asbolsyn.utils.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class HeaderInterceptor(private val headers: HashMap<String, String>?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        headers?.run {
            val builder = original.newBuilder()

            for ((key, value) in this) {
                builder.addHeader(key, value)
            }

            return chain.proceed(builder.method(original.method, original.body).build())
        }

        return chain.proceed(original.newBuilder().build())
    }
}