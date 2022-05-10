package com.practice.hagekotlin.screen.contents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.hagekotlin.model.CategoryContent
import com.practice.hagekotlin.storage.DataManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ContentsViewModel constructor(private val dataManager: DataManager) : ViewModel() {

    var contents = MutableLiveData<List<CategoryContent>>()

    fun load(categoryId: String?) {
        viewModelScope.launch {

            contents.value = when {
                categoryId.isNullOrEmpty() -> dataManager.contents
                else -> dataManager.contents.map { contents ->
                    contents.filter { it.categoryId == categoryId }
                }
            }.firstOrNull() ?: listOf(CategoryContent(headline = "NO_CONTENT"))
        }
    }
}