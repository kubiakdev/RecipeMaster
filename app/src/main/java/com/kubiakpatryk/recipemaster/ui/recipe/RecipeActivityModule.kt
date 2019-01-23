package com.kubiakpatryk.recipemaster.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import com.kubiakpatryk.recipemaster.di.annotation.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class RecipeActivityModule {

    @Binds
    @ActivityScope
    abstract fun bindRecipeActivity(activity: RecipeActivity): AppCompatActivity

}