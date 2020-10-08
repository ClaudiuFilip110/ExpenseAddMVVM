package com.example.expenceappmvvm.screens.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityMainBinding
import com.example.expenceappmvvm.databinding.ActivityTestBinding
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.main.MainViewModel
import kotlinx.android.synthetic.main.activity_test.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestActivity : AppCompatActivity() {
    private val viewModel: TestViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)
            .apply {
                lifecycleOwner = this@TestActivity
                testViewModel = viewModel
            }
        initObservers()
    }

    fun initObservers() {
        viewModel.userName.observe(this, Observer {
            viewModel.password.value = it
        })
    }
}