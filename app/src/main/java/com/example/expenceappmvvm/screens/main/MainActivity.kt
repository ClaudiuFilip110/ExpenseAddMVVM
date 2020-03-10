package com.example.expenceappmvvm.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityMainBinding
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel = get()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        viewModel.onCreate(savedInstanceState)

        initObsevables()
    }

    private fun initObsevables() {
        viewModel.backBtn.observe(this, Observer {
            // init adapters
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
