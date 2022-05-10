package com.practice.hagekotlin

import com.practice.hagekotlin.screen.launcher.LauncherViewModel
import com.practice.hagekotlin.screen.login.AccountRepository
import com.practice.hagekotlin.screen.login.AccountRepositoryImpl
import com.practice.hagekotlin.network.AccountService
import com.practice.hagekotlin.network.ServiceBuilder
import com.practice.hagekotlin.properties.Constant
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testNetworkModule = module {
    single { ServiceBuilder.build(Constant.BASE_URL_STAGING, AccountService::class.java) }
    single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
}

val testActivityModule = module {
    viewModel<LauncherViewModel> { LauncherViewModel(get()) }
}