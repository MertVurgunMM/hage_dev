package com.practice.hagekotlin.di

import com.practice.hagekotlin.login.LoginViewModel
import org.koin.dsl.module

val activityModule = module {
    single<LoginViewModel> { LoginViewModel(get()) }
}