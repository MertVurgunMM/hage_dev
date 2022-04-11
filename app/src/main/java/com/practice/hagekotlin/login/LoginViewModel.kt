package com.practice.hagekotlin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.hagekotlin.storage.CredentialsStore
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AccountRepository,
    private val store: CredentialsStore
) : ViewModel() {

    fun submitLogin(firstName: String, lastName: String, personalNumber: String) {
        viewModelScope.launch {
            try {
                repository.login(firstName, lastName, personalNumber)

                val isexpired = store.expired

                print("$isexpired")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}