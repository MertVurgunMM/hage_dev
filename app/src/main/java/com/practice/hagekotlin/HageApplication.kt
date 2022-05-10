package com.practice.hagekotlin

import android.app.Application
import android.util.Log
import com.practice.hagekotlin.di.activityModule
import com.practice.hagekotlin.di.appModule
import com.practice.hagekotlin.storage.DataManager
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.coroutines.CoroutineContext

class HageApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HageApplication)
            modules(appModule, activityModule)
        }
    }
}