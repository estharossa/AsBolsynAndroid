package com.example.asbolsyn.utils.network.data.model

sealed class Result<out T : Any> {
    fun value(): T? {
        return if (this is Success) this.data else null
    }

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}