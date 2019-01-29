package com.kubiakpatryk.recipemaster.ui.recipe.adapter.preparing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.ui.base.adapter.BaseAdapter
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.ingredient.RecipeIngredientsAdapterListener
import kotlinx.android.synthetic.main.item_preparing.view.*

class RecipePreparingAdapter(
    override var list: List<RecipePreparingItem>,
    override var listener: RecipeIngredientsAdapterListener
) : BaseAdapter<RecipePreparingItem, RecipePreparingAdapter.RecipePreparingViewHolder,
        RecipeIngredientsAdapterListener>(list, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipePreparingViewHolder =
        RecipePreparingViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_preparing, parent, false),
            listener = listener
        )

    class RecipePreparingViewHolder(
        private val view: View,
        override var listener: RecipeIngredientsAdapterListener
    ) : BaseAdapter.BaseViewHolder<RecipePreparingItem, RecipeIngredientsAdapterListener>(view, listener) {

        override fun bindHolder(item: RecipePreparingItem) {
            view.apply {
                tvPreparingItem.text = item.preparing
            }
        }

    }

}
