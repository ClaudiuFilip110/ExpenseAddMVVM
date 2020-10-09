package com.example.expenceappmvvm.screens.main.expenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.FragmentExpensesBinding
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.main.MainViewModel
import com.example.expenceappmvvm.screens.main.expenses.adapters.ViewPagerAdapter
import com.example.expenceappmvvm.screens.main.expenses.viewpager.ViewPagerFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_expenses.*
import kotlinx.android.synthetic.main.fragment_expenses.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExpensesFragment : Fragment() {
    private val viewModel: ExpensesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerAdapter(activity)
        tabMediator()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentExpensesBinding>(
            inflater,
            R.layout.fragment_expenses,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            expensesViewModel = viewModel
        }
        initObservers()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun tabMediator() {
        TabLayoutMediator(expenses_tab_layout, expenses_view_pager) { tab, position ->
            when (position) {
                0 ->
                    tab.text = "This week"
                1 ->
                    tab.text = "This month"
                2 ->
                    tab.text = "This year"
            }
        }.attach()
    }

    private fun setViewPagerAdapter(activity: FragmentActivity?) {
        activity?.let {
                expenses_view_pager.adapter =
                    ViewPagerAdapter(it)
        }
    }

    private fun initObservers() {

    }

    companion object {
        fun newInstance(position: Int): Fragment {
            val fragment =
                ViewPagerFragment()
            val args = Bundle()
//            args.putDouble("total", 0.0)
            when (position) {
                0 -> {
//                    args.putDouble("total", ExpensesView.week)
                    args.putString("name", Constants.WEEK)
                }
                1 -> {
//                    args.putDouble("total", ExpensesView.month)
                    args.putString("name", Constants.MONTH)
                }
                2 -> {
//                    args.putDouble("total", ExpensesView.year)
                    args.putString("name", Constants.YEAR)
                }
            }
            fragment.arguments = args
            return fragment
        }
    }
}