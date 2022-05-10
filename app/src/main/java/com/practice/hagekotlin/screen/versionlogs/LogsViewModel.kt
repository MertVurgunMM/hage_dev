package com.practice.hagekotlin.screen.versionlogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.practice.hagekotlin.storage.DataManager
import kotlinx.coroutines.launch

class LogsViewModel(dataManager: DataManager) : ViewModel() {
    val logs = dataManager.versionLogs.asLiveData()

    init {
        viewModelScope.launch {
            @Suppress("DeferredResultUnused")
            dataManager.loadData()
        }
    }
}