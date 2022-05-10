package com.practice.hagekotlin.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.practice.hagekotlin.R
import com.practice.hagekotlin.model.Category

@Composable
fun CategoryScreen(
    categories: List<Category>,
    onCategoryClick: (categoryId: String, categoryName: String) -> Unit
) {
    LazyColumn {
        items(categories) {
            Text(text = it.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.margin_text_row))
                    .clickable { onCategoryClick(it.id, it.name) })
        }
    }
}