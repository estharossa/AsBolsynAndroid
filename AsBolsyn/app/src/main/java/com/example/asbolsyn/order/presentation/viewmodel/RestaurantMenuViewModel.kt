package com.example.asbolsyn.order.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asbolsyn.order.data.model.MenuResponse
import com.example.asbolsyn.order.domain.usecase.LoadRestaurantMenu
import kotlinx.coroutines.launch

class RestaurantMenuViewModel(
    private val loadRestaurantMenu: LoadRestaurantMenu
) : ViewModel() {

    companion object {
        private const val MOCK_RESTAURANT_ID = "3ff0a4b6-67c3-4e4d-93ed-e8ea161e6d4d"
    }

    private val _menuState = MutableLiveData<MenuState>()
    val menuState: LiveData<MenuState>
        get() = _menuState

    fun dispatch(action: MenuAction) {
        when (action) {
            is MenuAction.LoadMenu -> loadMenu()
        }
    }

    private fun loadMenu() {
        changeLoadingState(true)

        viewModelScope.launch {
            loadRestaurantMenu(MOCK_RESTAURANT_ID).fold(
                onLeft = {
                    _menuState.value = MenuState.Error(it.message)
                },
                onRight = {
                    _menuState.value = MenuState.SubmitList(it)
                }
            ).also {
                changeLoadingState(false)
            }
        }
    }

    private fun changeLoadingState(isLoading: Boolean) {
        _menuState.value = MenuState.LoadingState(isLoading)
    }
}

sealed class MenuAction {
    object LoadMenu : MenuAction()
}

sealed class MenuState {
    data class LoadingState(val isLoading: Boolean) : MenuState()
    data class SubmitList(val menu: MenuResponse) : MenuState()
    data class Error(val message: String? = null) : MenuState()
}