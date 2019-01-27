package com.kubiakpatryk.recipemaster.ui.main

import com.kubiakpatryk.recipemaster.ui.base.BaseView

interface MainView : BaseView {

    fun showNetworkError()

    fun hideProgressBar()

    fun showAppTitle()

    fun showProgressBar()

    fun updateImage(url: String)

}
