package com.practice.hagekotlin.screen.category

import androidx.lifecycle.*
import com.practice.hagekotlin.storage.DataManager
import kotlinx.coroutines.launch

class CategoryViewModel constructor(
    private val dataManager: DataManager
) : ViewModel() {

    val categories = dataManager.categories.asLiveData()

    init {
        viewModelScope.launch {
            @Suppress("DeferredResultUnused")
            dataManager.loadData()
        }
    }
}