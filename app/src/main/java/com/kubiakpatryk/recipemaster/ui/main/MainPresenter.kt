package com.kubiakpatryk.recipemaster.ui.main

import com.kubiakpatryk.recipemaster.service.api.RecipeApi
import com.kubiakpatryk.recipemaster.service.model.RecipeModel
import com.kubiakpatryk.recipemaster.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(private val api: RecipeApi) : BasePresenter<MainView>() {

    var recipeModel: RecipeModel? = null
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
                        view?.showNetworkError()
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { model ->
                    recipeModel = model
                    view?.hideProgressBar()
                    model?.imgs?.get(RECIPE_IMAGE_URL_INDEX)?.let { url ->
                        view?.apply {
                            updateImage(url)
                            showAppTitle()
                        }
                    }

                },
                onError = {
                    view?.showNetworkError()
                }
            )
    )

    companion object {

        private const val TIMEOUT = 5L
        private const val RECIPE_IMAGE_URL_INDEX = 0

    }

}
