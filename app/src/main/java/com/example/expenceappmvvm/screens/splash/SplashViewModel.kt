package com.example.expenceappmvvm.screens.splash

import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.screens.login.LoginActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashViewModel() : ViewModel() {
    fun showNextScreen(splashActivity: SplashActivity) {
        val timer = Timer()
        //TODO: You can use SingleLiveEvent and start Login Activity from activity
        timer.schedule(timerTask { LoginActivity.start(splashActivity) }, DELAY)
    }

    companion object {
        const val DELAY = 2000L
    }
}