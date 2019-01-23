package com.kubiakpatryk.recipemaster.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> : DaggerAppCompatActivity(),
    BaseView {

    abstract val layoutResId: Int

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(layoutResId)
        attachToPresenter()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    @Suppress("UNCHECKED_CAST")
    private fun attachToPresenter() {
        presenter.view = this as? V
    }

}