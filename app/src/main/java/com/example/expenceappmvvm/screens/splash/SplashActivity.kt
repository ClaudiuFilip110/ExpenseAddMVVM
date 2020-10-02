package com.example.expenceappmvvm.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.screens.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.showNextScreen(this)
    }

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, SplashActivity::class.java))
        }
    }
}