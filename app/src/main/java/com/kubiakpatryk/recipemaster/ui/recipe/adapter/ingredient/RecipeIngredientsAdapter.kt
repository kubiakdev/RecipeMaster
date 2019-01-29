package com.kubiakpatryk.recipemaster.ui.recipe.adapter.ingredient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.ui.base.adapter.BaseAdapter
import kotlinx.android.synthetic.main.item_ingredient.view.*

class RecipeIngredientsAdapter(
    override var list: List<RecipeIngredientItem>,
    override var listener: RecipeIngredientsAdapterListener
) : BaseAdapter<RecipeIngredientItem, RecipeIngredientsAdapter.RecipeIngredientsViewHolder,
        RecipeIngredientsAdapterListener>(list, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientsViewHolder =
        RecipeIngredientsViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false),
            listener = listener
        )

    class RecipeIngredientsViewHolder(
        private val view: View,
        override var listener: RecipeIngredientsAdapterListener
    ) : BaseAdapter.BaseViewHolder<RecipeIngredientItem, RecipeIngredientsAdapterListener>(view, listener) {

        override fun bindHolder(item: RecipeIngredientItem) {
            view.apply {
                tvIngredientItem.text = item.ingredient
            }
        }

    }

}
