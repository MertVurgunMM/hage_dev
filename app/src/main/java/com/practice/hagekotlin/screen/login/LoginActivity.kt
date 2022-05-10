package com.practice.hagekotlin.screen.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import com.practice.hagekotlin.screen.MainActivity
import com.practice.hagekotlin.R
import com.practice.hagekotlin.databinding.ScreenComposeBinding
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by inject()

    private lateinit var binding: ScreenComposeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_compose)

        binding = ScreenComposeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeWindow.setContent {
            LoginScreen(
                uiState = viewModel.state.collectAsState(),
                submitBehavior = viewModel::submitLogin,
                onSuccessBehavior = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            )
        }
    }
}