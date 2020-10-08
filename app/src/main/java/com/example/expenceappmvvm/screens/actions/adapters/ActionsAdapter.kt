package com.example.android_resources.screens.action.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.databinding.RecyclerviewActionBinding
import com.example.expenceappmvvm.domain.models.CategoryModel
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.actions.ActionViewModel
import kotlinx.android.synthetic.main.recyclerview_action.view.*

class ActionsAdapter(
    private var mActions: ArrayList<CategoryModel>,
    private var viewModel: ActionViewModel
) :
    RecyclerView.Adapter<ActionsAdapter.ActionsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActionsViewHolder {
        val binding = DataBindingUtil.inflate<RecyclerviewActionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_action,
            parent,
            false
        )
        return ActionsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mActions.size
    }

    override fun onBindViewHolder(holder: ActionsViewHolder, position: Int) {
        holder.bind(mActions[position])
    }

    inner class ActionsViewHolder(private val binding: RecyclerviewActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryModel) {
            binding.action = item

            itemView.setOnClickListener {
                mActions.find { it.isSelected }
                    .let {
                        if (it != null) {
                            it.isSelected = false
                            notifyItemChanged(mActions.indexOf(it))
                        }
                    }
                //change to selected
                item.apply {
                    isSelected = true
                    notifyItemChanged(mActions.indexOf(item))
                }
                viewModel.category.value = item.itemCategory
            }
        }
    }
}