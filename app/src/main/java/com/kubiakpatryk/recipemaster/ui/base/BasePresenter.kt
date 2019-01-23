package com.kubiakpatryk.recipemaster.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V : BaseView> {

    var view: V? = null
    private val compositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun destroy() {
        compositeDisposable.clear()
    }

}