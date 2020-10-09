package com.example.expenceappmvvm.screens.main.expenses.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color.green
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.databinding.ExpensesRecyclerviewAdapterBinding
import com.example.expenceappmvvm.databinding.RecyclerviewActionBinding
import com.example.expenceappmvvm.domain.CategoryEnum
import com.example.expenceappmvvm.domain.models.RecyclerModel
import com.example.expenceappmvvm.domain.util.DateAndTimeUtils
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.actions.ActionActivity
import kotlinx.android.synthetic.main.expenses_recyclerview_adapter.view.*

class ExpensesRecyclerAdapter(
    val context: Context,
    val mPayments: ArrayList<Action>,
    val mTotal: ArrayList<Double>,
    val resources: Resources = Resources.getSystem()
) :
    RecyclerView.Adapter<ExpensesRecyclerAdapter.ExpensesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val binding = DataBindingUtil.inflate<ExpensesRecyclerviewAdapterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.expenses_recyclerview_adapter,
            parent,
            false
        )
        return ExpensesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mPayments.size
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        if (mPayments.size == 0 || mTotal.size == 0)
            return
        holder.bind(mPayments[position], mTotal[position])
    }

    inner class ExpensesViewHolder(private val binding: ExpensesRecyclerviewAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentPayment: Action,
            currentTotal: Double
        ) {
            val curImage = currentPayment.category.toLowerCase()
            val drawableResourceId: Int =
                context.resources.getIdentifier(curImage, "drawable", context.packageName)

            binding.recyclerViewModel = RecyclerModel()
            binding.recyclerViewModel?.amount = currentPayment.amount.toString()
            binding.recyclerViewModel?.category.let {
                it?.itemCategory = currentPayment.category
                it?.itemIcon = CategoryEnum.values()
                    .findLast { enum -> enum.getStringValue() == currentPayment.category }
                    .let { enum -> enum!!.getCategoryIcon() }
            }
            binding.recyclerViewModel?.totalAmount = currentTotal.toString()
            binding.recyclerViewModel?.day =
                DateAndTimeUtils.convertSimpleDate(currentPayment.date).toString()
            binding.recyclerViewModel?.category.apply {
                if (currentPayment.category == "Income") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.cardExpensesAmount.setTextColor(
                            resources.getColor(
                                R.color.green_dark,
                                null
                            )
                        )
                        binding.cardExpensesType.setTextColor(
                            resources.getColor(
                                R.color.green_dark,
                                null
                            )
                        )
                    }
                    this?.itemCategory = "Income"
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.cardExpensesAmount.setTextColor(resources.getColor(R.color.red, null))
                        binding.cardExpensesType.setTextColor(resources.getColor(R.color.red, null))
                    }

                    this?.itemCategory = "Expense"
                }
            }
            binding.cardExpensesCard.setOnClickListener {
                val intent = Intent(context as Activity, ActionActivity::class.java)
//                intent.putExtra(passedObject, currentPayment)
                context.startActivity(intent)
            }
        }
    }
}