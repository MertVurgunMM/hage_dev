package com.practice.hagekotlin.screen.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject

class LauncherActivity : AppCompatActivity() {

    private val viewModel: LauncherViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.screenState.observe(this) { targetScreen ->
            startActivity(Intent(this, targetScreen))
            finish()
        }
    }
}