package com.example.asbolsyn.main.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asbolsyn.main.data.model.CategoriesResponse
import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.main.domain.Usecase.LoadCategories
import com.example.asbolsyn.main.domain.Usecase.LoadRestaurants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RestaurantsViewModel(
    private val loadRestaurants: LoadRestaurants,
    private val loadCategories: LoadCategories
) : ViewModel() {

    companion object {
        private const val MOCK_ROOM_NUMBER = "A6F81R"
    }

    private val _restaurantsState = MutableLiveData<RestaurantsState>()
    val restaurantsState: LiveData<RestaurantsState>
        get() = _restaurantsState

    private val restaurants: MutableList<RestaurantsResponse.Item> = mutableListOf()
    private val categories: MutableList<CategoriesResponse.Item> = mutableListOf()

    fun dispatch(action: RestaurantsAction) {
        when (action) {
            is RestaurantsAction.FetchRestaurants -> fetchCategories()
            is RestaurantsAction.OrderPlace -> orderPlace()
        }
    }

    private fun orderPlace() {
        changeLoadingState(true)

        viewModelScope.launch {
            // todo: fake order

            delay(1500)
            _restaurantsState.value = RestaurantsState.OrderSuccess(MOCK_ROOM_NUMBER)

            changeLoadingState(false)
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
                    restaurants.clear()
                    restaurants.addAll(it.restaurants)
                    _restaurantsState.value = RestaurantsState.RestaurantsLoaded(restaurants, categories)
                }
            ).also {
                changeLoadingState(false)
            }
        }
    }

    private fun fetchCategories() {
        changeLoadingState(true)

        viewModelScope.launch {
            loadCategories(Unit).fold(
                onLeft = {
                    _restaurantsState.value = RestaurantsState.Error(it.message)
                },
                onRight = {
                    categories.clear()
                    categories.addAll(it.category)
                    fetchRestaurants()
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
    object OrderPlace : RestaurantsAction()
}

sealed class RestaurantsState {
    data class LoadingState(val isLoading: Boolean) : RestaurantsState()
    data class Error(val message: String? = null) : RestaurantsState()
    data class RestaurantsLoaded(
        val restaurants: List<RestaurantsResponse.Item>,
        val categories: List<CategoriesResponse.Item>
    ) : RestaurantsState()

    data class OrderSuccess(val roomNumber: String) : RestaurantsState()
}