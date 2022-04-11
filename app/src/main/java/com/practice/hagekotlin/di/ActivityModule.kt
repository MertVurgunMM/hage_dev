package com.practice.hagekotlin.di

import com.practice.hagekotlin.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {
    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
}