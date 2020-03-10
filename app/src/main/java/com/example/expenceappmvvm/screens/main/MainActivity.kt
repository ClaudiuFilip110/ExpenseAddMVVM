package com.example.expenceappmvvm.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expenceappmvvm.databinding.ActivityMainBinding
import org.koin.android.ext.android.get
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel = get()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
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
