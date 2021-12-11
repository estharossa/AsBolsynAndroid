package com.example.asbolsyn.utils.network.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import kotlin.reflect.KClass
import com.example.asbolsyn.utils.network.data.model.Result

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> T,
        exceptionClass: KClass<out Exception>? = null
    ): Result<T> {
        return try {
            Result.Success(call())
        } catch (e: HttpException) {
            handleHttpException(e, exceptionClass)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun handleHttpException(
        e: HttpException,
        kClass: KClass<out Exception>?
    ): Result.Error {
        val source = e.response()?.errorBody() ?: return Result.Error(e)

        return try {
            val apiException = parseException(source.string(), kClass ?: HttpException::class)
            Result.Error(apiException)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun parseException(json: String, kClass: KClass<out Exception>): Exception {
        return try {
            Gson().fromJson(json, TypeToken.getParameterized(kClass.java).type)
        } catch (e: Exception) {
            e
        }
    }
}