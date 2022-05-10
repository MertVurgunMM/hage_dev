package com.practice.hagekotlin.di

import com.practice.hagekotlin.network.AccountService
import com.practice.hagekotlin.network.FileService
import com.practice.hagekotlin.network.ServiceBuilder
import com.practice.hagekotlin.properties.Constant
import com.practice.hagekotlin.screen.login.AccountRepository
import com.practice.hagekotlin.screen.login.AccountRepositoryImpl
import com.practice.hagekotlin.storage.Credentials
import com.practice.hagekotlin.storage.CredentialsManager
import com.practice.hagekotlin.storage.DataManager
import com.practice.hagekotlin.utils.AssetReader
import com.practice.hagekotlin.utils.DownloadManager
import com.practice.hagekotlin.utils.FileManager
import com.practice.hagekotlin.utils.NetworkManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single<AccountService> { ServiceBuilder.build(Constant.BASE_URL, AccountService::class.java) }
    single<FileService> { ServiceBuilder.build(Constant.FILE_SERVICE_URL, FileService::class.java) }
    single<AccountRepository> { AccountRepositoryImpl(get(), get()) }

    single<Credentials> { CredentialsManager(androidApplication().applicationContext) }
    single<NetworkManager> { NetworkManager(androidApplication().applicationContext) }

    single { AssetReader(androidApplication().applicationContext) }
    single { FileManager(androidApplication().applicationContext, get()) }
    single { DataManager(androidApplication().applicationContext, get(), get(), get()) }
    single { DownloadManager(androidApplication().applicationContext, get(), get(), get()) }
}