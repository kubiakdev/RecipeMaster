package com.kubiakpatryk.recipemaster.ui.recipe

import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.ui.base.BaseActivity

class RecipeActivity : BaseActivity<RecipeView, RecipePresenter>(), RecipeView {

    override val layoutResId: Int = R.layout.activity_recipe

}
