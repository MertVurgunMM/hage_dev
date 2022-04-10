package com.practice.hagekotlin

import android.app.Application
import com.practice.hagekotlin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HageApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HageApplication)
            modules(appModule)
        }
    }

}