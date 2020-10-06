package com.example.expenceappmvvm.screens.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.login.LoginActivity
import com.facebook.stetho.Stetho
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.timerTask


class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        viewModel.deleteUser()
        initObservables()
        viewModel.initAutoLoginUser()
    }

    fun initObservables() {
        viewModel.userExists.observe(this, Observer {
            if (it) {
                viewModel.startMain(this)
                Log.d("autologinuser", viewModel.autoLoginUser.value.toString())
            } else {
                viewModel.startLogin(this)
                Log.d("autologinuser", viewModel.autoLoginUser.value.toString())
            }
        })
    }

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, SplashActivity::class.java))
        }
    }
}