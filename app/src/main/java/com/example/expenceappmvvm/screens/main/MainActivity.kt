package com.example.expenceappmvvm.screens.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityMainBinding
import com.example.expenceappmvvm.screens.login.LoginActivity
import com.example.expenceappmvvm.screens.main.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainActivity
        }

        mainViewModel.onCreate()
        initDrawerSlider()
        observeRedirectActions()
        initViewPagerAdapter()
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closerDrawer()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.onDestroy()
    }

    private fun initDrawerSlider() {
        object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                with(holder) {
                    translationX = drawerView.width * slideOffset
                    scaleX = 1 - slideOffset / DRAWER_SCALE
                    scaleY = 1 - slideOffset / DRAWER_SCALE
                }
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }.apply {
            drawerLayout.addDrawerListener(this)
            drawerLayout.setScrimColor(Color.TRANSPARENT)
            syncState()
        }
    }

    private fun closerDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun observeRedirectActions() {
        mainViewModel.shouldGoToConvertActivity.observe(this, Observer {
            closerDrawer()
        })

        mainViewModel.shouldGoToAddActivity.observe(this, Observer {
            // Open Add Activity
        })

        mainViewModel.shouldGoToLoginActivity.observe(this, Observer {
            LoginActivity.starLogin(this)
        })
    }

    private fun initViewPagerAdapter() {
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
    }

    companion object {

        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        }

        const val DRAWER_SCALE = 7f
    }
}
