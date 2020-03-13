package com.example.expenceappmvvm.screens.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.expenceappmvvm.screens.main.fragments.budget.BudgetFragment
import com.example.expenceappmvvm.screens.main.fragments.expenses.ExpensesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BudgetFragment()
            1 -> ExpensesFragment()
            else -> Fragment()
        }
    }

    companion object {
        const val CARD_ITEM_SIZE = 2
    }
}