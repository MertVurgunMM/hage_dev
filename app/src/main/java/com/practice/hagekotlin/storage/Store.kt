package com.practice.hagekotlin.storage

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.practice.hagekotlin.model.Category
import com.practice.hagekotlin.model.CategoryContent
import com.practice.hagekotlin.model.VersionLog
import com.practice.hagekotlin.network.FileService
import com.practice.hagekotlin.properties.Constant
import com.practice.hagekotlin.properties.Constant.DURATION_2_DAYS
import com.practice.hagekotlin.properties.Constant.UNDEFINED
import com.practice.hagekotlin.utils.AssetReader.DataAsset.*
import com.practice.hagekotlin.utils.FileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.IOException

interface Credentials {
    val state: AccessState
    fun put(firstName: String, lastName: String, personalNo: String)
    fun get(block: (firstName: String?, lastName: String?, personalNo: String?) -> Unit)
    fun clear()
}

internal class CredentialsManager(context: Context) : Credentials {

    private val prefs = context.getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE)

    override val state: AccessState
        get() {
            var mutableState: AccessState = AccessState.UNAUTHORIZED
            get { firstName, lastName, personalNo ->
                if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || personalNo.isNullOrEmpty()) {
                    mutableState = AccessState.UNAUTHORIZED
                }
            }

            prefs.getString(KEY_UPDATED_TIME, null)?.let { lastUpdatedTime ->
                mutableState =
                    when {
                        System.currentTimeMillis() - lastUpdatedTime.toLong() > DURATION_2_DAYS -> {
                            AccessState.EXPIRED
                        }
                        else -> AccessState.AUTHORIZED
                    }
            }

            return mutableState
        }

    override fun put(firstName: String, lastName: String, personalNo: String) {
        prefs.edit()
            .putString(KEY_FIRST_NAME, firstName)
            .putString(KEY_LAST_NAME, lastName)
            .putString(KEY_PERSONAL_NO, personalNo)
            .putString(KEY_UPDATED_TIME, System.currentTimeMillis().toString())
            .apply()
    }

    override fun get(block: (firstName: String?, lastName: String?, personalNo: String?) -> Unit) {
        block(
            prefs.getString(KEY_FIRST_NAME, null),
            prefs.getString(KEY_LAST_NAME, null),
            prefs.getString(KEY_PERSONAL_NO, null)
        )
    }

    override fun clear() {
        prefs.edit()
            .putString(KEY_FIRST_NAME, null)
            .putString(KEY_LAST_NAME, null)
            .putString(KEY_PERSONAL_NO, null)
            .putString(KEY_UPDATED_TIME, null)
            .apply()

    }

    private companion object {
        const val PREFERENCE_ID = "LoginCredentials"
        const val KEY_FIRST_NAME = "firstName"
        const val KEY_LAST_NAME = "lastName"
        const val KEY_PERSONAL_NO = "personalNo"
        const val KEY_UPDATED_TIME = "updatedTime"
    }
}

enum class AccessState {
    UNAUTHORIZED, AUTHORIZED, EXPIRED
}

class DataManager(
    private val context: Context,
    private val fileService: FileService,
    private val fileManager: FileManager,
    private val credentials: Credentials
) {
    val categories = MutableStateFlow<List<Category>>(emptyList())
    val contents = MutableStateFlow<List<CategoryContent>>(emptyList())
    val versionLogs = MutableStateFlow<List<VersionLog>>(emptyList())

    init {
        fileManager.generateEmptyFile("categories.json")
        fileManager.generateEmptyFile("contents.json")
        fileManager.generateEmptyFile("logs.json")
    }

    @Suppress("DeferredResultUnused", "DeferredIsResult")
    suspend fun loadData() = withContext(Dispatchers.IO) {
        async { categories.emit(fileManager.getData(CATEGORIES)) }
        async { contents.emit(fileManager.getData(CONTENTS)) }
        async {
            versionLogs.emit(fileManager.getData<VersionLog>(LOGS).filter {
                it.text != Constant.UNDEFINED
            })
        }
    }

    @Throws(IOException::class)
    suspend fun updateCategories() = withContext(Dispatchers.IO) {
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

        val categoriesData = fileService.getCategories(firstName, lastName, personalNo)

        fileManager.writeToFile(
            context,
            JSONArray(Gson().toJson(categoriesData).toString()).toString(),
            "categories.json"
        )
        categories.emit(categoriesData) //TODO Check if main issue
    }

    @Throws(IOException::class)
    suspend fun updateContents() = withContext(Dispatchers.IO) {

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
        val contentsData = fileService.getContents(firstName, lastName, personalNo)

        fileManager.writeToFile(
            context,
            JSONArray(Gson().toJson(contentsData).toString()).toString(),
            "contents.json"
        )
        contents.emit(contentsData)
    }

    @Throws(IOException::class)
    suspend fun updateLogs() = withContext(Dispatchers.IO) {
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
        val logsData = fileService.getLogs(firstName, lastName, personalNo)
        fileManager.writeToFile(
            context,
            JSONArray(Gson().toJson(logsData).toString()).toString(),
            "logs.json"
        )
        versionLogs.emit(logsData.filter { it.text != UNDEFINED })
    }

    suspend fun updateData() = withContext(Dispatchers.IO) {
        Log.i("DATA", "Update all started!")
        try {
            updateCategories()
            updateContents()
            updateLogs()
            Log.i("DATA", "Update all completed!")
        } catch (e: Exception) {
            Log.e("DATA", "Update all failed!", e)
        }
    }
}