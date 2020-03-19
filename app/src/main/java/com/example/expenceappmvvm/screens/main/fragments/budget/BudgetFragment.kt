package com.example.expenceappmvvm.screens.main.fragments.budget


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.FragmentBudgetBinding
import org.koin.android.ext.android.get

class BudgetFragment : Fragment() {

    private val budgetViewModel: BudgetViewModel = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentBudgetBinding>(
            inflater,
            R.layout.fragment_budget,
            container,
            false
        ).apply {
            lifecycleOwner = this@BudgetFragment
            viewModel = budgetViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        budgetViewModel.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        budgetViewModel.onDestroy()
    }
}
