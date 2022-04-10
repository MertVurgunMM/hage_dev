package com.practice.hagekotlin

import com.practice.hagekotlin.login.AccountRepository
import com.practice.hagekotlin.login.AccountRepositoryImpl
import com.practice.hagekotlin.network.AccountService
import com.practice.hagekotlin.network.ServiceBuilder
import com.practice.hagekotlin.properties.Constant
import org.koin.dsl.module

val testNetworkModule = module {
    single { ServiceBuilder.build(Constant.BASE_URL_STAGING, AccountService::class.java) }
    single<AccountRepository> { AccountRepositoryImpl(get()) }
}