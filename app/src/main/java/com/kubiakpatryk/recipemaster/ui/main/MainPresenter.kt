package com.kubiakpatryk.recipemaster.ui.main

import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.service.api.RecipeApi
import com.kubiakpatryk.recipemaster.service.model.RecipeModel
import com.kubiakpatryk.recipemaster.ui.base.BasePresenter
import com.kubiakpatryk.recipemaster.util.RECIPE_PREVIEW_IMAGE_URL_INDEX
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(private val api: RecipeApi) : BasePresenter<MainView>() {

    lateinit var recipeModel: RecipeModel
        private set

    init {
        view?.showProgressBar()
        getRecipeFromApi()
    }

    private fun getRecipeFromApi() = add(
        api.getRecipe()
            .subscribeOn(Schedulers.io())
            .retryWhen {
                it
                    .delay(TIMEOUT, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        view?.showAnnouncement(R.string.global_network_error)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { model ->
                    recipeModel = model
                    view?.apply {
                        hideProgressBar()
                        model?.imgs?.get(RECIPE_PREVIEW_IMAGE_URL_INDEX)?.let { url ->
                            updateImage(url)
                            showAppTitle()
                        }
                    }

                },
                onError = {
                    view?.showAnnouncement(R.string.global_network_error)
                }
            )
    )

    companion object {

        private const val TIMEOUT = 5L

    }

}
