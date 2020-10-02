package com.example.expenceappmvvm.screens.splash

import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.prefs.PreferencesService

class SplashViewModel(private val sharedPreferences: PreferencesService) : ViewModel() {
    companion object {
        private const val SPLASH_DELAY = 2000L
    }
}