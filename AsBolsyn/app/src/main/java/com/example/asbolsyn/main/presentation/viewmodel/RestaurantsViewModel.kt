package com.example.asbolsyn.main.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.main.domain.LoadRestaurants
import kotlinx.coroutines.launch

class RestaurantsViewModel(
    private val loadRestaurants: LoadRestaurants
) : ViewModel() {

    private val _restaurantsState = MutableLiveData<RestaurantsState>()
    val restaurantsState: LiveData<RestaurantsState>
        get() = _restaurantsState

    fun dispatch(action: RestaurantsAction) {
        when (action) {
            is RestaurantsAction.FetchRestaurants -> fetchRestaurants()
        }
    }

    private fun fetchRestaurants() {
        changeLoadingState(true)

        viewModelScope.launch {
            loadRestaurants(Unit).fold(
                onLeft = {
                    _restaurantsState.value = RestaurantsState.Error(it.message)
                },
                onRight = {
                    _restaurantsState.value = RestaurantsState.RestaurantsLoaded(it.restaurants)
                }
            ).also {
                changeLoadingState(false)
            }
        }
    }

    private fun changeLoadingState(isLoading: Boolean) {
        _restaurantsState.value = RestaurantsState.LoadingState(isLoading)
    }
}

sealed class RestaurantsAction {
    object FetchRestaurants : RestaurantsAction()
}

sealed class RestaurantsState {
    data class LoadingState(val isLoading: Boolean) : RestaurantsState()
    data class Error(val message: String? = null) : RestaurantsState()
    data class RestaurantsLoaded(val restaurants: List<RestaurantsResponse.Item>) : RestaurantsState()
}