package com.example.asbolsyn.auth.di

import com.example.asbolsyn.auth.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authPresentationModule = module {
    viewModel {
        AuthViewModel()
    }
}

val authModules = listOf(authPresentationModule)