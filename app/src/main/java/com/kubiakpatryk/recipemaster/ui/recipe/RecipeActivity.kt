package com.kubiakpatryk.recipemaster.ui.recipe

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.service.model.RecipeModel
import com.kubiakpatryk.recipemaster.ui.base.BaseActivity
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.image.RecipeImageAdapter
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.image.RecipeImageAdapterListener
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.image.RecipeImageItem
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.ingredient.RecipeIngredientItem
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.ingredient.RecipeIngredientsAdapter
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.ingredient.RecipeIngredientsAdapterListener
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.preparing.RecipePreparingAdapter
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.preparing.RecipePreparingAdapterListener
import com.kubiakpatryk.recipemaster.ui.recipe.adapter.preparing.RecipePreparingItem
import com.kubiakpatryk.recipemaster.util.EXTRA_FULL_NAME
import com.kubiakpatryk.recipemaster.util.EXTRA_RECIPE
import com.kubiakpatryk.recipemaster.util.RECIPE_PREVIEW_IMAGE_URL_INDEX
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_recipe.*
import java.io.File


class RecipeActivity : BaseActivity<RecipeView, RecipePresenter>(),
    RecipeView,
    RecipeIngredientsAdapterListener,
    RecipePreparingAdapterListener,
    RecipeImageAdapterListener {

    override val layoutResId: Int = R.layout.activity_recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.extras?.apply {
            if (containsKey(EXTRA_RECIPE)) {
                getParcelable<RecipeModel>(EXTRA_RECIPE)?.let {
                    setUpView(it)
                }
            }

            if (containsKey(EXTRA_FULL_NAME)) {
                tvRecipeLoggedInAs.text =
                    String.format(getString(R.string.activity_main_logged_in_as), getString(EXTRA_FULL_NAME))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun checkPermissionsAndSaveBitmapToGallery(url: String) {
        RxPermissions(this)
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribeBy(
                onNext = { granted ->
                    if (granted) {
                        saveImageToGallery(url)
                    }
                },
                onError = { it.printStackTrace() }
            )
    }

    private fun saveImageToGallery(url: String) {
        (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(
            DownloadManager.Request(Uri.parse(url)).apply {
                allowScanningByMediaScanner()
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File(url).name)
            }
        )

        registerReceiver(
            object : BroadcastReceiver() {

                override fun onReceive(context: Context, intent: Intent) {
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            R.string.activity_recipe_image_downloaded,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

            },
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun setUpView(recipe: RecipeModel) {
        tvRecipeTitle.text = recipe.title
        tvRecipeDescription.text = recipe.description

        rvRecipeImages.apply {
            recipe.imgs?.apply {
                layoutManager = object : GridLayoutManager(context, SPAN_COUNT) {

                    override fun canScrollVertically(): Boolean = false

                }

                adapter = RecipeImageAdapter(
                    list = filterIndexed { i, _ -> i != RECIPE_PREVIEW_IMAGE_URL_INDEX }
                        .map { RecipeImageItem(it) },
                    listener = this@RecipeActivity
                )
            }
        }

        rvRecipeIngredients.apply {
            recipe.ingredients?.apply {
                layoutManager = object : LinearLayoutManager(context) {

                    override fun canScrollVertically(): Boolean = false

                }

                adapter = RecipeIngredientsAdapter(
                    list = map { RecipeIngredientItem(it) },
                    listener = this@RecipeActivity
                )
            }
        }

        rvRecipePreparing.apply {
            recipe.preparing?.apply {
                layoutManager = object : LinearLayoutManager(context) {

                    override fun canScrollVertically(): Boolean = false

                }

                adapter = RecipePreparingAdapter(
                    list = map { RecipePreparingItem(it) },
                    listener = this@RecipeActivity
                )
            }
        }
    }

    companion object {

        private const val SPAN_COUNT = 2

        fun newIntent(
            context: Context,
            recipe: RecipeModel,
            fullName: String
        ) = Intent(context, RecipeActivity::class.java).apply {
            putExtra(EXTRA_RECIPE, recipe)
            putExtra(EXTRA_FULL_NAME, fullName)
        }

    }

}
