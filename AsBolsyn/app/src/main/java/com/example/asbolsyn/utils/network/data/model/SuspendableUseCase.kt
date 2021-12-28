package com.example.asbolsyn.utils.network.data.model

interface SuspendableUseCase<in Params: Any, out Type : Any> {
    suspend fun run(params: Params): Either<Throwable, Type>

    suspend operator fun invoke(params: Params): Either<Throwable, Type> = run(params)
}