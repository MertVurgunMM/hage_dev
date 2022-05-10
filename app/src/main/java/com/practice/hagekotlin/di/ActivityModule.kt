package com.practice.hagekotlin.di

import com.practice.hagekotlin.screen.MainViewModel
import com.practice.hagekotlin.screen.category.CategoryViewModel
import com.practice.hagekotlin.screen.contents.ContentsViewModel
import com.practice.hagekotlin.screen.launcher.LauncherViewModel
import com.practice.hagekotlin.screen.login.LoginViewModel
import com.practice.hagekotlin.screen.pdfviewer.PdfViewerViewModel
import com.practice.hagekotlin.screen.versionlogs.LogsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {
    viewModel<MainViewModel> { MainViewModel(get(), get()) }
    viewModel<LauncherViewModel> { LauncherViewModel(get()) }
    viewModel<LoginViewModel> { LoginViewModel(get(), get(), get()) }
    viewModel<ContentsViewModel> { ContentsViewModel(get()) }
    viewModel<CategoryViewModel> { CategoryViewModel(get()) }
    viewModel<LogsViewModel> { LogsViewModel(get()) }
    viewModel<PdfViewerViewModel> { PdfViewerViewModel(get(), get()) }
}