package com.practice.hagekotlin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AccountRepository) : ViewModel() {

    fun submitLogin(firstName: String, lastName: String, personalNumber: String) {
        viewModelScope.launch {
            try {
                repository.login(firstName, lastName, personalNumber)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}