package com.kubiakpatryk.recipemaster.di

import com.kubiakpatryk.recipemaster.App
import com.kubiakpatryk.recipemaster.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class AppBuilder : AndroidInjector.Builder<App>()

}
