package com.example.expenceappmvvm.screens.splash

import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.screens.login.LoginActivity
import com.example.expenceappmvvm.screens.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

class SplashViewModel(private val sharedPreferences: PreferencesService) : ViewModel() {

    fun showNextScreen(splashActivity: SplashActivity) {
        val timer = Timer()
        GlobalScope.launch {
            if (sharedPreferences.getUserID() != null) {
                timer.schedule(timerTask { showMainScreen(splashActivity) }, SPLASH_DELAY)
            } else {
                timer.schedule(timerTask { showLoginScreen(splashActivity) }, SPLASH_DELAY)
            }
        }
    }

    private fun showMainScreen(splashActivity: SplashActivity) {
        MainActivity.start(splashActivity)
    }

    private fun showLoginScreen(splashActivity: SplashActivity) {
        LoginActivity.starLogin(splashActivity)
        splashActivity.finish()
    }

    companion object {
        private const val SPLASH_DELAY = 2000L
    }
}