package com.practice.hagekotlin.login

import android.app.Activity
import android.os.Bundle
import com.practice.hagekotlin.R
import org.koin.android.ext.android.inject

class LoginActivity: Activity() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.submitLogin("Google", "Play Store", "009999")
    }
}