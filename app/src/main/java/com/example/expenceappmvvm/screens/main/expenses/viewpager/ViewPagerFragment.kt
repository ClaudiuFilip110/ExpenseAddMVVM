package com.example.expenceappmvvm.screens.main.expenses.viewpager

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_resources.screens.action.adapters.ActionsAdapter
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.databinding.FragmentExpensesBinding
import com.example.expenceappmvvm.databinding.FragmentViewPagerBinding
import com.example.expenceappmvvm.screens.main.expenses.PieChart
import com.example.expenceappmvvm.screens.main.expenses.adapters.ExpensesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_view_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ViewPagerFragment : Fragment() {
    private val viewModel: ViewPagerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentViewPagerBinding>(
            inflater,
            R.layout.fragment_view_pager,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewPagerViewModel = viewModel
        }
        initObservers()
        initCurrentPagerNr()
        return binding.root
    }

    private fun initCurrentPagerNr() {
        viewModel.title = arguments?.get("name").toString()
    }

    private fun initAdapter(list: List<Action>) {
        expenses_recycler_view.apply {
            layoutManager =
                LinearLayoutManager(context)
            val doubleList: ArrayList<Double> = ArrayList()
            for (item in list) doubleList.add(item.amount)
            adapter =
                ExpensesRecyclerAdapter(context, viewModel.adapterList(), doubleList, resources)
        }
    }

    private fun initObservers() {
        viewModel.actions.observe(viewLifecycleOwner, Observer {
            initAdapter(it)
            initChart(it)
        })
    }

    fun initChart(list: List<Action>) {

        val pieChart = PieChart(returnActionsArray(list))
        view?.let { pieChart.instantiate(it) }
    }

    private fun returnActionsArray(it: List<Action>?): ArrayList<Action> {
        val list = ArrayList<Action>()
        if (it != null) {
            for (el in it) {
                list.add(el)
            }
        }
        return list
    }
}