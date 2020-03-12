package com.example.expenceappmvvm.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityLoginBinding
import com.example.expenceappmvvm.domain.util.InputTypesEnum
import com.example.expenceappmvvm.screens.main.MainActivity
import org.koin.android.ext.android.get

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            viewModel = this@LoginActivity.viewModel
            lifecycleOwner = this@LoginActivity
            passwordInputType = InputTypesEnum.PASSWORD
            emailInputType = InputTypesEnum.EMAIL
        }

        observeOnLogin()
    }

    private fun observeOnLogin() {
        viewModel.goToMainActivity.observe(this, Observer {
            MainActivity.start(this)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }

    companion object {
        fun starLogin(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }
}