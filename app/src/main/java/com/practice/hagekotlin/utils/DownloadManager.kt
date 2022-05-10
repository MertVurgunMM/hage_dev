package com.practice.hagekotlin.utils

import android.R.attr
import android.content.Context
import android.os.Environment
import android.system.Os.write
import android.util.Log
import com.practice.hagekotlin.network.FileService
import com.practice.hagekotlin.storage.Credentials
import com.practice.hagekotlin.utils.DownloadException.FileCreationFailed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.*


class DownloadManager(
    private val context: Context,
    private val fileService: FileService,
    private val credentials: Credentials,
    private val fileManager: FileManager
) {

    suspend fun download(path: String, complete: (fileName: String) -> Unit) =
        withContext(Dispatchers.IO) {
            var firstName = ""
            var lastName = ""
            var personalNo = ""

            credentials.get { _firstName, _lastName, _personalNo ->
                requireNotNull(_firstName)
                requireNotNull(_lastName)
                requireNotNull(_personalNo)

                firstName = _firstName
                lastName = _lastName
                personalNo = _personalNo
            }
            val fileName = path.substringAfter("pdfs/")

            val data = fileService.download(fileName, firstName, lastName, personalNo)




            try {
                fileManager.generateEmptyFile(fileName)

                val fileContents = data.bytes()

                context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                    it.write(fileContents)
                }
            } catch (e: IOException) {
                throw FileCreationFailed
            }
            complete.invoke(fileName)
        }

}

sealed class DownloadException : Exception() {
    object FileCreationFailed : DownloadException()
}