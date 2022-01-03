package com.example.asbolsyn.main.di

import com.example.asbolsyn.main.data.repository.RestaurantsRepositoryImpl
import com.example.asbolsyn.main.data.service.RestaurantsService
import com.example.asbolsyn.main.domain.usecase.LoadCategories
import com.example.asbolsyn.main.domain.usecase.LoadRestaurants
import com.example.asbolsyn.main.domain.repository.RestaurantsRepository
import com.example.asbolsyn.main.presentation.viewmodel.OrderViewModel
import com.example.asbolsyn.main.presentation.viewmodel.RestaurantsViewModel
import com.example.asbolsyn.utils.network.NetworkKit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authPresentationModule = module {
    viewModel {
        RestaurantsViewModel(
            loadRestaurants = get(),
            loadCategories = get()
        )
    }

    viewModel { OrderViewModel() }
}

private val authDomainModule = module {
    factory {
        LoadCategories(
            repository = get()
        )
    }

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