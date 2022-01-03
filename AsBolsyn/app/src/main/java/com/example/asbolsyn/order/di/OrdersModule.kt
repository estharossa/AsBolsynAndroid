package com.example.asbolsyn.order.di

import com.example.asbolsyn.order.data.repository.OrderRepositoryImpl
import com.example.asbolsyn.order.data.service.OrderService
import com.example.asbolsyn.order.domain.repository.OrderRepository
import com.example.asbolsyn.order.domain.usecase.LoadOrderDetails
import com.example.asbolsyn.order.domain.usecase.LoadOrders
import com.example.asbolsyn.order.presentation.viewmodel.CabinetOrdersViewModel
import com.example.asbolsyn.order.presentation.viewmodel.OrderDetailsViewModel
import com.example.asbolsyn.utils.network.NetworkKit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val ordersPresentationModule = module {
    viewModel {
        CabinetOrdersViewModel(
            fetchOrders = get()
        )
    }

    viewModel {
        OrderDetailsViewModel(
            fetchOrderDetails = get()
        )
    }
}

private val ordersDomainModule = module {
    factory {
        LoadOrderDetails(
            repository = get()
        )
    }

    factory {
        LoadOrders(
            repository = get()
        )
    }

    factory<OrderRepository> {
        OrderRepositoryImpl(
            service = get()
        )
    }
}

private val ordersDataModule = module {
    single<OrderService> {
        NetworkKit.createService()
    }
}

val ordersModule = ordersDataModule + ordersDomainModule + ordersPresentationModule