package com.practice.hagekotlin.utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.practice.hagekotlin.R
import com.practice.hagekotlin.model.Category
import com.practice.hagekotlin.model.CategoryContent
import com.practice.hagekotlin.model.VersionLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter

class FileManager(
    private val context: Context,
    private val assetReader: AssetReader
) {

    @Throws(IOException::class)
    fun generateEmptyFile(fileName: String, data: String = "") {
        context.getFileStreamPath(fileName)
            .takeIf { it?.exists() == false }
            ?.let { writeToFile(context, data, fileName) }
    }

    @Throws(IOException::class)
    fun writeToFile(context: Context, data: String, fileName: String) {
        OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            .apply { write(data) }
            .close()
    }

    @Throws(IOException::class)
    suspend fun getUri(fileName: String): Uri = withContext(Dispatchers.IO) {
        File(context.filesDir, fileName).toUri()
    }

    private fun getJson(asset: AssetReader.DataAsset): JSONArray? {
        val jsonData: String? = when (asset) {
            AssetReader.DataAsset.CATEGORIES ->
                assetReader.readJSONFromAsset(context.getString(R.string.FILENAME_CATEGORIES))
            AssetReader.DataAsset.CONTENTS ->
                assetReader.readJSONFromAsset(context.getString(R.string.FILENAME_CONTENTS))
            AssetReader.DataAsset.LOGS ->
                assetReader.readJSONFromAsset(context.getString(R.string.FILENAME_LOGS))
        }
        return jsonData.takeIf { !it.isNullOrEmpty() }?.let {
            JSONArray(it)
        }
    }


    fun <T> getData(asset: AssetReader.DataAsset): List<T> {

        val jsonArrayData = getJson(asset) ?: return emptyList()

        val items = mutableListOf<Any>()

        for (index in 0 until jsonArrayData.length())
            jsonArrayData.getJSONObject(index).let { jsonData ->
                items.add(
                    when (asset) {
                        AssetReader.DataAsset.CATEGORIES -> jsonData.toCategories()
                        AssetReader.DataAsset.CONTENTS -> jsonData.toContents()
                        AssetReader.DataAsset.LOGS -> jsonData.toVersionLogs()
                    }
                )
            }

        return items as List<T>
    }

    private fun JSONObject.toCategories() = Category(
        name = getString("name"),
        id = getString("id")
    )

    private fun JSONObject.toContents() = CategoryContent(
        id = getInt("id"),
        categoryId = getString("category_id"),
        headline = getString("headline"),
        path = getString("file")
    )

    private fun JSONObject.toVersionLogs() = VersionLog(
        id = getInt("id"),
        userId = getInt("user_id"),
        text = getString("text"),
        categoryId = getString("category_id"),
        contentId = getString("content_id")
    )

    fun isFileExist(fileName: String): Boolean {
        return context.getFileStreamPath(fileName).exists()
    }
}