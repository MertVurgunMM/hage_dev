package com.practice.hagekotlin.utils;

import android.content.Context
import java.io.IOException
import java.nio.charset.StandardCharsets

class AssetReader(private val context: Context) {

    fun readJSONFromAsset(filename: String?): String? {
        try {
            context.openFileInput(filename)?.run {
                val buffer = ByteArray(available())
                read(buffer)
                close()
                return String(buffer, StandardCharsets.UTF_8)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    enum class DataAsset {
        CATEGORIES, CONTENTS, LOGS;
    }
}