package com.kubiakpatryk.recipemaster.service.api

import com.kubiakpatryk.recipemaster.service.model.RecipeModel
import io.reactivex.Single
import retrofit2.http.GET

interface RecipeApi {

    @GET("info.php#")
    fun getRecipe(): Single<RecipeModel>

}
