package com.example.asbolsyn.order.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.domain.usecase.LoadOrderDetails
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val fetchOrderDetails: LoadOrderDetails
) : ViewModel() {

    companion object {
        private const val MOCK_ORDER_ID = "9cef2775-46f4-49ee-a452-70fdab3ffdd6"
    }

    private val _orderDetailsState = MutableLiveData<OrderDetailsState>()
    val orderDetailsState: LiveData<OrderDetailsState>
        get() = _orderDetailsState

    private var orderDetails: OrderDetailsResponse? = null

    fun dispatch(action: OrderDetailsAction) {
        when (action) {
            is OrderDetailsAction.LoadOrderDetails -> loadOrderDetails()
        }
    }

    private fun loadOrderDetails() {
        changeLoadingState(true)

        viewModelScope.launch {
            fetchOrderDetails(MOCK_ORDER_ID).fold(
                onLeft = {
                    _orderDetailsState.value = OrderDetailsState.Error(it.message)
                },
                onRight = {
                    orderDetails = it
                    constructUI()
                }
            ).also {
                changeLoadingState(false)
            }
        }
    }

    private fun constructUI() {
        _orderDetailsState.value = OrderDetailsState.SubmitList(generateAdapterItems())
    }

    private fun generateAdapterItems() = mutableListOf<DelegateAdapterItem>().apply {
        orderDetails?.let {

        }
    }

    private fun changeLoadingState(isLoading: Boolean) {
        _orderDetailsState.value = OrderDetailsState.LoadingState(isLoading)
    }
}

sealed class OrderDetailsAction {
    object LoadOrderDetails : OrderDetailsAction()
}

sealed class OrderDetailsState {
    data class LoadingState(val isLoading: Boolean) : OrderDetailsState()
    data class SubmitList(val items: List<DelegateAdapterItem>) : OrderDetailsState()
    data class Error(val message: String? = null) : OrderDetailsState()
}