package com.example.asbolsyn.auth.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.asbolsyn.R
import com.example.asbolsyn.auth.presentation.view.SmsConfirmationView
import com.example.asbolsyn.auth.presentation.viewmodel.AuthState
import com.example.asbolsyn.auth.presentation.viewmodel.AuthViewModel
import com.example.asbolsyn.databinding.ActivityLoginBinding
import com.example.asbolsyn.main.presentation.activity.MainActivity
import com.example.asbolsyn.utils.extensions.hideSoftKeyboard
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    companion object {
        private const val PHONE_INPUT_MASK = "+7 ([000]) [000]-[00]-[00]"
        private const val TAG = "PhoneAuthActivity"
    }

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        configureObservers()
    }

    private fun setupViews() {
        binding.phoneNumber.addTextChangedListener(getMaskedTextListener())
        binding.sendCodeButton.setOnClickListener {
            it.hideSoftKeyboard(this)
            authViewModel.validate(binding.phoneNumber.text.toString())
        }

        binding.smsConfirmationView.onChangeListener = SmsConfirmationView.OnChangeListener { code, isComplete ->
            if (isComplete) {
                binding.root.hideSoftKeyboard(this)
                authViewModel.checkCode(code)
            }
        }
    }

    private fun configureObservers() {
        authViewModel.state.observe(this) { state ->
            when (state) {
                is AuthState.SmsConfirmation -> showSmsConfirmation()
                is AuthState.LoadingState -> changeLoadingState(state.isLoading)
                is AuthState.InvalidPhoneNumber -> showValidationError()
                is AuthState.SmsCodeVerified -> startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this).also {
            finish()
        }
    }

    private fun showValidationError() {
        Snackbar
            .make(binding.root, getString(R.string.auth_phone_number_validation_error), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun changeLoadingState(isLoading: Boolean) {
        binding.container.isGone = isLoading
        binding.progressBar.isGone = !isLoading
    }

    private fun showSmsConfirmation() {
        with(binding) {
            smsVerificationLayout.isGone = false
            phoneNumberLayout.isGone = true
        }
    }

    private fun getMaskedTextListener() = MaskedTextChangedListener(
        format = PHONE_INPUT_MASK,
        autocomplete = false,
        field = binding.phoneNumber,
        listener = null,
        valueListener = null
    )
}