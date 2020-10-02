package com.example.expenceappmvvm.screens.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expenceappmvvm.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}