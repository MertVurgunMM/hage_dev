package com.practice.hagekotlin.storage

import android.content.Context
import com.practice.hagekotlin.properties.Constant

interface CredentialsStore {
    val expired: Boolean
    fun put(firstName: String, lastName: String, personalNo: String)
    fun get(block: (firstName: String?, lastName: String?, personalNo: String?) -> Unit)
}

internal class CredentialsStoreManager(context: Context) : CredentialsStore {

    private val prefs = context.getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE)

    override val expired: Boolean
        get() {
            prefs.getString(KEY_UPDATED_TIME, null)?.let { lastUpdatedTime ->
                if (System.currentTimeMillis() - lastUpdatedTime.toLong() > Constant.DURATION_2_DAYS) {
                    return true
                }
            }
            return false
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

    private companion object {
        const val PREFERENCE_ID = "LoginCredentials"
        const val KEY_FIRST_NAME = "firstName"
        const val KEY_LAST_NAME = "lastName"
        const val KEY_PERSONAL_NO = "personalNo"
        const val KEY_UPDATED_TIME = "updatedTime"
    }
}