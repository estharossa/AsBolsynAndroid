package com.example.asbolsyn.order.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.order.domain.usecase.LoadOrders
import kotlinx.coroutines.launch

class CabinetOrdersViewModel(
    private val fetchOrders: LoadOrders
) : ViewModel() {

    private val _orderListState = MutableLiveData<OrderListState>()
    val orderListState: LiveData<OrderListState>
        get() = _orderListState

    fun dispatch(action: OrderListAction) {
        when (action) {
            is OrderListAction.LoadOrders -> loadOrders()
        }
    }

    private fun loadOrders() {
        changeLoadingState(true)

        viewModelScope.launch {
            fetchOrders(Unit).fold(
                onLeft = {
                    _orderListState.value = OrderListState.Error(it.message)
                },
                onRight = {
                    _orderListState.value = OrderListState.SubmitList(it)
                }
            ).also {
                changeLoadingState(false)
            }
        }
    }

    private fun changeLoadingState(isLoading: Boolean) {
        _orderListState.value = OrderListState.LoadingState(isLoading)
    }
}

sealed class OrderListAction {
    object LoadOrders : OrderListAction()
}

sealed class OrderListState {
    data class LoadingState(val isLoading: Boolean) : OrderListState()
    data class SubmitList(val orders: List<OrderItemResponse>) : OrderListState()
    data class Error(val message: String? = null) : OrderListState()
}