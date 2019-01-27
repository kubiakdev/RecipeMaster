package com.kubiakpatryk.recipemaster.di.module

import com.kubiakpatryk.recipemaster.di.annotation.ActivityScope
import com.kubiakpatryk.recipemaster.ui.main.MainActivity
import com.kubiakpatryk.recipemaster.ui.main.MainActivityModule
import com.kubiakpatryk.recipemaster.ui.recipe.RecipeActivity
import com.kubiakpatryk.recipemaster.ui.recipe.RecipeActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class, NetworkModule::class])
abstract class AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [RecipeActivityModule::class])
    abstract fun recipeActivityInjector(): RecipeActivity

}
