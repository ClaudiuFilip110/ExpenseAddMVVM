package com.example.expenceappmvvm.screens.main.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityMainBinding
import com.example.expenceappmvvm.databinding.FragmentBudgetBinding
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.main.MainActivity
import com.example.expenceappmvvm.screens.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetFragment : Fragment() {
    private val viewModel: BudgetViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = DataBindingUtil.inflate<FragmentBudgetBinding>(
            inflater,
            R.layout.fragment_budget,
            container,
            false
        )
            .apply {
                lifecycleOwner = viewLifecycleOwner
                budgetViewModel = viewModel
            }
        initObservers()
        return v.root
    }

    private fun initObservers() {
        viewModel.currentBalance.observe(viewLifecycleOwner, Observer {
        })
    }
}