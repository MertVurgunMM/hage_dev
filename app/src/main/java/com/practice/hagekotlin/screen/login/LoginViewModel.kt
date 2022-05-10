package com.practice.hagekotlin.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.hagekotlin.screen.login.LoginState.Failed.Reason
import com.practice.hagekotlin.storage.Credentials
import com.practice.hagekotlin.utils.NetworkManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class LoginViewModel(
    private val repository: AccountRepository,
    private val credentials: Credentials,
    private val network: NetworkManager
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Initial)
    val state: StateFlow<LoginState> = _state

    private val handler = CoroutineExceptionHandler { _, exception ->
        _state.value = when (exception) {
            is SocketTimeoutException -> LoginState.Failed(Reason.TIMEOUT)
            is IOException -> LoginState.Failed(Reason.CONNECTIVITY)
            else -> LoginState.Failed(Reason.INCORRECT_CREDENTIALS)
        }
        viewModelScope.launch {
            delay(200L)
            _state.value = LoginState.Initial
        }
    }

    init {
        if (!network.isAvailable()) {
            _state.value = LoginState.Failed(Reason.CONNECTIVITY)
        }
    }

    fun submitLogin(firstName: String, lastName: String, personalNumber: String) {
        viewModelScope.launch(handler) {
            _state.value = LoginState.Loading
            _state.value =
                when (val isSuccessful = repository.login(firstName, lastName, personalNumber)) {
                    isSuccessful -> LoginState.Authorized
                    else -> LoginState.Failed(Reason.INCORRECT_CREDENTIALS)
                }
        }
    }
}

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    object Authorized : LoginState()
    class Failed(val reason: Reason) : LoginState() {
        enum class Reason {
            CONNECTIVITY,
            INCORRECT_CREDENTIALS,
            TIMEOUT
        }
    }
}