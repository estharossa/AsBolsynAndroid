package com.example.asbolsyn.di


import com.example.asbolsyn.auth.di.authModules
import com.example.asbolsyn.main.di.restaurantsModule
import com.example.asbolsyn.order.di.ordersModule


val modules = authModules + restaurantsModule + ordersModule