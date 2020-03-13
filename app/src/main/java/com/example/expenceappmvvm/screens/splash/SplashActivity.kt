package com.example.expenceappmvvm.screens.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expenceappmvvm.R
import org.koin.android.ext.android.get

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.showNextScreen(this)
    }
}