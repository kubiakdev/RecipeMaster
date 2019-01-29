package com.kubiakpatryk.recipemaster.ui.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<I : BaseItem, H : BaseAdapter.BaseViewHolder<I, L>, L : BaseAdapterListener>(
    open var list: List<I>,
    open var listener: L
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<I, L>>() {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H

    override fun onBindViewHolder(holder: BaseViewHolder<I, L>, position: Int) {
        holder.bindHolder(list[position])
    }

    override fun getItemCount(): Int = list.size

    abstract class BaseViewHolder<I : BaseItem, L : BaseAdapterListener>(
        view: View,
        protected open var listener: L
    ) : RecyclerView.ViewHolder(view) {

        abstract fun bindHolder(item: I)

    }

}
