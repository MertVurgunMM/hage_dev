package com.practice.hagekotlin.screen.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.hagekotlin.screen.MainActivity
import com.practice.hagekotlin.screen.login.LoginActivity
import com.practice.hagekotlin.storage.AccessState
import com.practice.hagekotlin.storage.Credentials

class LauncherViewModel(private val credentials: Credentials) : ViewModel() {

    val _screenState = MutableLiveData<Class<*>>()
    val screenState: LiveData<Class<*>> = _screenState

    init {
        checkCredentials()
    }

    fun checkCredentials() {
        when (credentials.state) {
            AccessState.UNAUTHORIZED -> {
                navigateToLogin()
            }
            AccessState.AUTHORIZED -> {
                navigateToMain()
            }
            AccessState.EXPIRED -> {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        _screenState.value = LoginActivity::class.java
    }

    private fun navigateToMain() {
        _screenState.value = MainActivity::class.java
    }
}