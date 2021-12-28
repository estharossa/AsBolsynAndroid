package com.example.asbolsyn.utils.network.data.model

sealed class Either<out L : Any, out R : Any> {
    data class Left<out L : Any>(val left: L) : Either<L, Nothing>()
    data class Right<out R : Any>(val right: R) : Either<Nothing, R>()

    fun <L : Any> left(left: L) = Left(left)
    fun <R : Any> right(right: R) = Right(right)

    fun fold(onLeft: (L) -> Unit, onRight: (R) -> Unit): Any =
        when (this) {
            is Left -> onLeft(left)
            is Right -> onRight(right)
        }
}