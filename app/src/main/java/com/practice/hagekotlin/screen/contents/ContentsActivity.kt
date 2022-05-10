package com.practice.hagekotlin.screen.contents

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practice.hagekotlin.R
import com.practice.hagekotlin.databinding.ScreenComposeBinding
import com.practice.hagekotlin.screen.pdfviewer.PdfActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContentsActivity : AppCompatActivity() {

    private lateinit var binding: ScreenComposeBinding

    val viewModel: ContentsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenComposeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.contents.observe(this) { contents ->

            binding.composeWindow.setContent {
                ContentsScreen(contents) { fileName ->
                    startActivity(Intent(this, PdfActivity::class.java).apply {
                        putExtra(PdfActivity.KEY_FILENAME, fileName)
                    })
                }
            }
        }

        title =
            getString(R.string.category_content_name, intent.extras?.getString(KEY_CATEGORY_NAME))
        viewModel.load(intent.extras?.getString(KEY_CATEGORY_ID))
    }

    companion object {
        const val KEY_CATEGORY_ID = "keyCategoryId"
        const val KEY_CATEGORY_NAME = "keyTitle"
    }
}