package com.example.expenceappmvvm.screens.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityMainBinding
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.main.budget.BudgetFragment
import com.example.expenceappmvvm.screens.main.expenses.ExpensesFragment
import com.example.expenceappmvvm.screens.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                lifecycleOwner = this@MainActivity
                mainViewModel = viewModel
            }
        viewModel.toolbarText.value = "My Budget"
        initObservers()
        initToolbar(drawer_layout, toolbar)
        initFragment()
        initBtmNav()
    }

    private fun initObservers() {
        viewModel.toolbarText.observe(this, Observer {

        })
    }

    private fun initToolbar(
        drawer_layout: DrawerLayout,
        toolbar: androidx.appcompat.widget.Toolbar
    ) {
        supportActionBar
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            (R.string.open),
            (R.string.close)
        ) {}
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.colorAccent)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun initFragment() {
        val budgetFragment = BudgetFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment, budgetFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    private fun initBtmNav() {
        main_btmNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btm_nav_budget -> {
                    main_btmNav.menu.findItem(it.itemId).isChecked = true
                    main_btmNav.menu.findItem(R.id.btm_nav_expenses).isChecked = false
                    val budgetFragment = BudgetFragment()
                    viewModel.toolbarText.value = "My Budget"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, budgetFragment).commit()
                }
                R.id.btm_nav_expenses -> {
                    main_btmNav.menu.findItem(it.itemId).isChecked = false
                    main_btmNav.menu.findItem(R.id.btm_nav_budget).isChecked = false
                    val expensesFragment = ExpensesFragment()
                    viewModel.toolbarText.value = "My Expenses"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, expensesFragment).commit()
                }
            }
            true
        }
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}