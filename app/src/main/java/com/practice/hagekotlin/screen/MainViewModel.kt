package com.practice.hagekotlin.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practice.hagekotlin.storage.Credentials
import com.practice.hagekotlin.storage.DataManager
import kotlinx.coroutines.launch

class MainViewModel(
    private val credentials: Credentials,
    private val dataManager: DataManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            dataManager.updateData()
        }
    }

    fun greeting(block: (firstName: String, lastName: String) -> Unit) {
        credentials.get { firstName, lastName, _ ->
            requireNotNull(firstName) // TODO: error handling
            requireNotNull(lastName) // TODO: error handling

            block.invoke(firstName, lastName)
        }
    }

    fun logout() {
        credentials.clear()
    }
}