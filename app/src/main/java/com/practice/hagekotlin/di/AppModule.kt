package com.practice.hagekotlin.di

import com.practice.hagekotlin.login.AccountRepository
import com.practice.hagekotlin.login.AccountRepositoryImpl
import com.practice.hagekotlin.network.AccountService
import com.practice.hagekotlin.network.ServiceBuilder
import com.practice.hagekotlin.properties.Constant
import com.practice.hagekotlin.storage.CredentialsStore
import com.practice.hagekotlin.storage.CredentialsStoreManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single<AccountService> { ServiceBuilder.build(Constant.BASE_URL, AccountService::class.java) }
    single<AccountRepository> { AccountRepositoryImpl(get(), get()) }

    single<CredentialsStore> { CredentialsStoreManager(androidApplication().applicationContext) }
}