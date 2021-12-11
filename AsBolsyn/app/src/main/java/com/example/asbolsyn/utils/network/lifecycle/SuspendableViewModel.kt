package com.example.asbolsyn.utils.network.lifecycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class SuspendableViewModel: ViewModel(), CoroutineScope {

    val main: CoroutineContext = Dispatchers.Main
    val io: CoroutineContext = Dispatchers.IO

    @Suppress("MemberVisibilityCanBePrivate")
    protected var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + main


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}