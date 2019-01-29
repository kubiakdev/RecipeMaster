package com.kubiakpatryk.recipemaster.ui.main

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.ui.base.BaseActivity
import com.kubiakpatryk.recipemaster.ui.recipe.RecipeActivity
import com.kubiakpatryk.recipemaster.util.gone
import com.kubiakpatryk.recipemaster.util.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {

    override val layoutResId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fabMainRecipe.setOnClickListener {
            startActivity(RecipeActivity.newIntent(this, presenter.recipeModel))
            //todo snackbar if user is not logged
        }

        fabMainFacebook.setOnClickListener {
            //todo implement facebook login
        }
    }

    override fun updateImage(url: String) {
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_placeholder)
            )
            .into(civRecipeImage)
    }

    override fun showNetworkError() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Snackbar.make(clSnackBar, R.string.global_network_error, Snackbar.LENGTH_SHORT).apply {
                view.elevation = 0f
            }.show()
        } else {
            Toast.makeText(this, R.string.global_network_error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showAppTitle() {
        tvMainTitle.visible()
    }

    override fun showProgressBar() {
        pbMain.visible()
    }

    override fun hideProgressBar() {
        pbMain.gone()
    }

}
