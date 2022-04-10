package com.practice.hagekotlin.di

import com.practice.hagekotlin.network.AccountService
import com.practice.hagekotlin.network.ServiceBuilder
import com.practice.hagekotlin.properties.Constant
import org.koin.dsl.module

val appModule = module {

    single<AccountService> { ServiceBuilder.build(Constant.BASE_URL, AccountService::class.java) }
}