package com.practice.hagekotlin.screen.pdfviewer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.barteksc.pdfviewer.PDFView
import com.practice.hagekotlin.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PdfActivity : AppCompatActivity() {

    private val viewModel: PdfViewerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pdf_viewer)

        val fileName = intent.extras?.getString(KEY_FILENAME)
        val progressBar = findViewById<View>(R.id.progress)

        fun loadViewer(fileName: String?) {
            findViewById<PDFView>(R.id.pdf_view)
                .fromFile(File(filesDir, fileName))
                .onLoad {
                    Log.i("PDF Viewer", fileName.toString())
                }
                .onError {
                    it.printStackTrace()
                }
                .load()
        }

        viewModel.state.observe(this) { state ->

            when (state) {
                PdfViewerState.DownloadCompleted -> {
                    loadViewer(fileName)
                    progressBar.isVisible = false
                }
                PdfViewerState.DownloadError -> {
                    progressBar.isVisible = false
                }
                PdfViewerState.DownloadStarted -> {
                    progressBar.isVisible = true
                }
                PdfViewerState.FileAccessError -> {
                    progressBar.isVisible = false
                }
                PdfViewerState.FileAvailable -> {
                    progressBar.isVisible = true
                    loadViewer(fileName)
                    progressBar.isVisible = false
                }
                PdfViewerState.MissingFileName -> {
                    Log.e("PDF", "MissingFileName")
                }
            }
        }

        viewModel.load(fileName)
    }


    companion object {
        const val KEY_FILENAME = "keyFileName"
    }
}