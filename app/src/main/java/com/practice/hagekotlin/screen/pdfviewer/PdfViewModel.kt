package com.practice.hagekotlin.screen.pdfviewer

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.practice.hagekotlin.utils.AssetReader
import com.practice.hagekotlin.utils.DownloadManager
import com.practice.hagekotlin.utils.FileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class PdfViewerViewModel constructor(
    private val downloadManager: DownloadManager,
    private val fileManager: FileManager
) : ViewModel() {

    val _state: MutableStateFlow<PdfViewerState> = MutableStateFlow(PdfViewerState.Initial)
    val state = _state.asLiveData()

    fun download(fileName: String?) {
        if (fileName.isNullOrEmpty()) {
            _state.update { PdfViewerState.MissingFileName }
            Log.e("PDF Download", "File name is not available!")
            return
        }

        _state.update { PdfViewerState.DownloadStarted }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("PDF Download", "Started $fileName")

                downloadManager.download(fileName) { fileName ->
                    _state.update { PdfViewerState.DownloadCompleted }
                    Log.i("PDF Download", "Completed $fileName")
                }
            } catch (e: Exception) {
                _state.update { PdfViewerState.DownloadError }
                Log.e("PDF Download", "Failed to download!", e)
            }
        }
    }

    fun uriOf(fileName: String?, block: (uri: Uri) -> Unit) {
        if (fileName.isNullOrEmpty()) {
            _state.update { PdfViewerState.MissingFileName }
            Log.e("PDF", "File name is not available!")
            return
        }
        viewModelScope.launch {
            try {
                block.invoke(fileManager.getUri(fileName))
            } catch (e: Exception) {
                Log.e("PDF", "File could not open!", e)
                _state.update { PdfViewerState.FileAccessError }
            }
        }
    }

    fun load(fileName: String?) {
        if (fileName.isNullOrEmpty()) {
            _state.update { PdfViewerState.MissingFileName }
            Log.e("PDF", "File name is not available!")
            return
        }

        viewModelScope.launch {
            try {
                if (fileManager.isFileExist(fileName)) {
                    Log.i("PDF File", "Available $fileName")
                    _state.update { PdfViewerState.FileAvailable }
                } else {
                    Log.i("PDF File", "Not Available $fileName")
                    download(fileName)
                }
            } catch (e: Exception) {
                Log.e("PDF", "File could not open!", e)
                _state.update { PdfViewerState.FileAccessError }
            }
        }
    }
}

sealed class PdfViewerState {
    object Initial : PdfViewerState()
    object FileAvailable : PdfViewerState()
    object DownloadStarted : PdfViewerState()
    object DownloadCompleted : PdfViewerState()
    object DownloadError : PdfViewerState()
    object MissingFileName : PdfViewerState()
    object FileAccessError : PdfViewerState()
}