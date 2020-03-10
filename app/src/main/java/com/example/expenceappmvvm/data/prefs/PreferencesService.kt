package com.example.expenceappmvvm.data.prefs

import android.app.Application
import android.content.SharedPreferences
import com.example.expenceappmvvm.R
import com.google.gson.Gson
import java.util.*

class PreferencesService(
    private val appContext: Application, private val prefs: SharedPreferences,
    private val gson: Gson
) {

}