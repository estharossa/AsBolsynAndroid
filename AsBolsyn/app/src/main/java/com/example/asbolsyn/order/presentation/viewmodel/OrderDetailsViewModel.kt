package com.example.asbolsyn.order.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.domain.usecase.LoadOrderDetails
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsGuestsAdapterModel
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsHeaderAdapterModel
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsMenuAdapterModel
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsPricingAdapterModel
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
            is OrderDetailsAction.InviteFriends -> inviteFriends()
        }
    }

    private fun inviteFriends() {
        orderDetails?.let {
            _orderDetailsState.value = OrderDetailsState.InviteFriends(it)
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
            add(OrderDetailsHeaderAdapterModel(it))
            add(OrderDetailsMenuAdapterModel(it))
            if (it.guestsNumber > 0) add(OrderDetailsGuestsAdapterModel(it))
            add(OrderDetailsPricingAdapterModel(it))
        }
    }

    private fun changeLoadingState(isLoading: Boolean) {
        _orderDetailsState.value = OrderDetailsState.LoadingState(isLoading)
    }
}

sealed class OrderDetailsAction {
    object LoadOrderDetails : OrderDetailsAction()
    object InviteFriends : OrderDetailsAction()
}

sealed class OrderDetailsState {
    data class LoadingState(val isLoading: Boolean) : OrderDetailsState()
    data class SubmitList(val items: List<DelegateAdapterItem>) : OrderDetailsState()
    data class Error(val message: String? = null) : OrderDetailsState()
    data class InviteFriends(val orderDetails: OrderDetailsResponse) : OrderDetailsState()
}