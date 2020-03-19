package com.example.expenceappmvvm.screens.expenses.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ItemCategoryBinding
import com.example.expenceappmvvm.domain.models.CategoryModel
import com.example.expenceappmvvm.screens.expenses.AddExpensesViewModel


class ExpenseCategoriesAdapter(
    private val itemsList: MutableList<CategoryModel>,
    private val viewModel: AddExpensesViewModel
) : RecyclerView.Adapter<ExpenseCategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val bindingItem: ItemCategoryBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(item: CategoryModel) {
            bindingItem.expense = item
            itemView.setOnClickListener {
                viewModel.expense.value!!.category = item.itemCategory
                updateModels(item)
            }
        }
    }

    private fun updateModels(model: CategoryModel) {
        itemsList.find { it.isSelected }?.let {
            it.isSelected = false
            notifyItemChanged(itemsList.indexOf(it))
        }

        model.apply {
            isSelected = true
            notifyItemChanged(itemsList.indexOf(model))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category,
                parent,
                false
            )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }
}
