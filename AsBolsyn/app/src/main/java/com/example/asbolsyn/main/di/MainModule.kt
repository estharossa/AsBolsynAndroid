package com.example.asbolsyn.main.di

import com.example.asbolsyn.main.data.RestaurantsRepositoryImpl
import com.example.asbolsyn.main.data.service.RestaurantsService
import com.example.asbolsyn.main.domain.LoadRestaurants
import com.example.asbolsyn.main.domain.RestaurantsRepository
import com.example.asbolsyn.main.presentation.viewmodel.RestaurantsViewModel
import com.example.asbolsyn.utils.network.NetworkKit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authPresentationModule = module {
    viewModel {
        RestaurantsViewModel(
            loadRestaurants = get()
        )
    }
}

private val authDomainModule = module {
    factory {
        LoadRestaurants(
            repository = get()
        )
    }

    factory<RestaurantsRepository> {
        RestaurantsRepositoryImpl(
            service = get()
        )
    }
}

private val authDataModule = module {
    single<RestaurantsService> {
        NetworkKit.createService()
    }
}

val restaurantsModule = authPresentationModule + authDomainModule + authDataModule