package com.kubiakpatryk.recipemaster.ui.recipe.adapter.image

import com.kubiakpatryk.recipemaster.ui.base.adapter.BaseAdapterListener

interface RecipeImageAdapterListener : BaseAdapterListener {

    fun checkPermissionsAndSaveBitmapToGallery(url: String)

}
