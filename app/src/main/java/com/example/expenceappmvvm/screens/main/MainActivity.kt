package com.example.expenceappmvvm.screens.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityMainBinding
import com.example.expenceappmvvm.domain.util.UIUtils.inFromRightAnimation
import com.example.expenceappmvvm.domain.util.UIUtils.outToRightAnimation
import com.example.expenceappmvvm.domain.util.UIUtils.viewScaleDown
import com.example.expenceappmvvm.domain.util.UIUtils.viewScaleUp
import com.example.expenceappmvvm.screens.login.LoginActivity
import com.example.expenceappmvvm.screens.main.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel = get()
    private var indicatorWidth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainActivity
        }

        mainViewModel.onCreate()
        initDrawerSlider()
        initViewPagerAdapter()
        initTabLayoutMediator()
        observeRedirectActions()
        calculateIndicatorWidth()
        setTabLayoutListener()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closerDrawer()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {`
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

    private fun initTabLayoutMediator() {
        TabLayoutMediator(bottomTabLayout, viewPager) { tab, position ->
            with(tab) {
                when (position) {
                    0 -> {
                        text = getString(R.string.my_budget)
                        icon = getDrawable(R.drawable.icon_money)
                    }
                    1 -> {
                        text = getString(R.string.expenses)
                        icon = getDrawable(R.drawable.icon_expensive)
                    }
                }
            }

        }.attach()
    }

    private fun calculateIndicatorWidth() {
        bottomTabLayout.post {
            indicatorWidth = bottomTabLayout.width / bottomTabLayout.tabCount

            val indicatorParams = indicator.layoutParams as LinearLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            indicator.layoutParams = indicatorParams
        }
    }

    private fun setTabLayoutListener() {
        viewScaleUp(bottomTabLayout.getTabAt(0)!!.view)
        bottomTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                (indicator.layoutParams as LinearLayout.LayoutParams).apply {
                    leftMargin = tab.position * indicatorWidth
                    indicator.layoutParams = this
                }

                val position = tab.position
                if (position == 0) {
                    indicator.animation = inFromRightAnimation()
                    viewScaleUp(bottomTabLayout.getTabAt(position)!!.view)
                    viewScaleDown(bottomTabLayout.getTabAt(position + 1)!!.view)

                } else if (position == 1) {
                    indicator.animation = outToRightAnimation()
                    viewScaleUp(bottomTabLayout.getTabAt(position)!!.view)
                    viewScaleDown(bottomTabLayout.getTabAt(position - 1)!!.view)

                }
            }
        })
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
