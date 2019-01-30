package com.kubiakpatryk.recipemaster.ui.main

import androidx.annotation.StringRes
import com.kubiakpatryk.recipemaster.ui.base.BaseView

interface MainView : BaseView {

    fun showAnnouncement(@StringRes resId: Int)

    fun hideProgressBar()

    fun showAppTitle()

    fun showProgressBar()

    fun updateImage(url: String)

}
