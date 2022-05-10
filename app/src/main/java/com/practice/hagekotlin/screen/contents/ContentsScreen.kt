package com.practice.hagekotlin.screen.contents

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
import com.practice.hagekotlin.model.CategoryContent

@Composable
fun ContentsScreen(
    contents: List<CategoryContent>,
    onContentClick: (fileName: String?) -> Unit
) {
    LazyColumn {
        items(contents) {
            Text(text = it.headline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.margin_text_row))
                    .clickable {
                        onContentClick(it.path?.substringAfter("pdfs/"))
                    })
        }
    }
}
