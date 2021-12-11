package com.example.asbolsyn.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asbolsyn.utils.phoneGoodFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(
) : ViewModel() {

    companion object {
        private const val KZ_PHONE_NUMBER_REGEX = "^\\+?77([0124567][0-8]\\d{7})\$"
        private const val DEFAULT_TIME_OUT = 60
    }

    private val _state = MutableLiveData<AuthState>()
    val state: LiveData<AuthState>
        get() = _state

    fun checkCode(code: String) {
        changeLoadingState(true)

        viewModelScope.launch {
            delay(2000L)
            if (code == "4444") {
                _state.value = AuthState.SmsCodeVerified
            } else {

            }
            changeLoadingState(false)
        }
    }

    fun validate(phoneNumber: String) {
        if (!phoneNumber.phoneGoodFormat().matches(KZ_PHONE_NUMBER_REGEX.toRegex())) {
            _state.value = AuthState.InvalidPhoneNumber
            return
        }

        sendCode(phoneNumber)
    }

    private fun sendCode(phoneNumber: String) {
        changeLoadingState(true)

        viewModelScope.launch {
            delay(2000L)
            _state.value = AuthState.SmsConfirmation
            changeLoadingState(false)
        }
    }

    private fun changeLoadingState(isLoading: Boolean) {
        _state.value = AuthState.LoadingState(isLoading)
    }

}

sealed class AuthState {
    object SmsConfirmation : AuthState()
    object InvalidPhoneNumber : AuthState()
    object SmsCodeVerified : AuthState()
    data class LoadingState(val isLoading: Boolean) : AuthState()
}