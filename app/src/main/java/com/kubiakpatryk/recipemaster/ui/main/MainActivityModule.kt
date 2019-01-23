package com.kubiakpatryk.recipemaster.ui.main

import androidx.appcompat.app.AppCompatActivity
import com.kubiakpatryk.recipemaster.di.annotation.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    @ActivityScope
    abstract fun bindMainActivity(activity: MainActivity): AppCompatActivity

}