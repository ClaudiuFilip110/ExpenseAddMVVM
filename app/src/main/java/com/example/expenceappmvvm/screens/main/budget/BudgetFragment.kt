package com.example.expenceappmvvm.screens.main.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.databinding.FragmentBudgetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class BudgetFragment : Fragment() {
    private val viewModel: BudgetViewModel by viewModel()

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

    fun setChart(actions: ArrayList<Action>) {
        val data = BarData(actions, resources).apply {
            view?.let { configureChartAppearance(it) }
        }
        var chartData = data.createChartData()
        view?.let { data.prepareChartData(it, chartData) }
    }

    private fun initObservers() {
        viewModel.currentBalance.observe(viewLifecycleOwner, Observer {
        })

        viewModel.actions.observe(viewLifecycleOwner, Observer {
            val array: ArrayList<Action> =
                ArrayList(it)
            setChart(array)
        })
    }
}