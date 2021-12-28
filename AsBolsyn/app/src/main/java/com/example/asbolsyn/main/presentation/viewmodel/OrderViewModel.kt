package com.example.asbolsyn.main.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class OrderViewModel : ViewModel() {

    private val _orderState = MutableLiveData<OrderState>()
    val orderState: LiveData<OrderState>
        get() = _orderState

    private var timeInHours = "12"
    private var timeInMinutes = "00"
    private var selectedAmountOfSeats = 1
    private var maxSeats = 0

    fun dispatch(action: OrderAction) {
        when (action) {
            is OrderAction.InitOrder -> initOrder()
            is OrderAction.IncrementTime -> incrementTime()
            is OrderAction.DecrementTime -> decrementTime()
            is OrderAction.IncrementSeat -> incrementSeat()
            is OrderAction.DecrementSeat -> decrementSeat()
        }
    }

    private fun decrementSeat() {
        if (selectedAmountOfSeats == 1) return

        selectedAmountOfSeats -= 1
        _orderState.value = OrderState.UpdateSeats(selectedAmountOfSeats)
    }

    private fun incrementSeat() {
        if (selectedAmountOfSeats == maxSeats) return

        selectedAmountOfSeats += 1
        _orderState.value = OrderState.UpdateSeats(selectedAmountOfSeats)
    }

    private fun incrementTime() {
        if (timeInHours == "23" && timeInMinutes == "30") {
            timeInHours = "12"
            timeInMinutes = "00"
            showUpdatedTime()
            return
        }

        if (timeInMinutes == "00") {
            timeInMinutes = "30"
            showUpdatedTime()
            return
        }

        if (timeInMinutes == "30") {
            timeInMinutes = "00"
            timeInHours = (timeInHours.toInt() + 1).toString()
            showUpdatedTime()
            return
        }
    }

    private fun decrementTime() {
        if (timeInHours == "12" && timeInMinutes == "00") {
            return
        }

        if (timeInMinutes == "00") {
            timeInMinutes = "30"
            timeInHours = (timeInHours.toInt() - 1).toString()
            showUpdatedTime()
            return
        }

        if (timeInMinutes == "30") {
            timeInMinutes = "00"
            showUpdatedTime()
            return
        }
    }

    private fun showUpdatedTime() {
        _orderState.value = OrderState.UpdateTime(formatTime())
    }

    private fun formatTime() = "$timeInHours:$timeInMinutes"

    private fun initOrder() {
        // todo: fix fake data

        val freeTables = Random.nextInt(1, 10)
        val maxSeats = Random.nextInt(4, 10)
        this.maxSeats = maxSeats

        _orderState.value = OrderState.SetupUI(freeTables, maxSeats)
    }
}

sealed class OrderAction {
    object InitOrder : OrderAction()
    object IncrementTime : OrderAction()
    object DecrementTime : OrderAction()
    object IncrementSeat : OrderAction()
    object DecrementSeat : OrderAction()
}

sealed class OrderState {
    data class SetupUI(val freeTables: Int, val maxSeats: Int) : OrderState()
    data class UpdateTime(val time: String) : OrderState()
    data class UpdateSeats(val seats: Int) : OrderState()
}