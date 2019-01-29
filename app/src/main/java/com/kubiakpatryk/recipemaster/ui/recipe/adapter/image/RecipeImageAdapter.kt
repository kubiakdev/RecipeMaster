package com.kubiakpatryk.recipemaster.ui.recipe.adapter.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.ui.base.adapter.BaseAdapter
import kotlinx.android.synthetic.main.item_image.view.*


class RecipeImageAdapter(
    override var list: List<RecipeImageItem>,
    override var listener: RecipeImageAdapterListener
) : BaseAdapter<RecipeImageItem, RecipeImageAdapter.RecipeImageViewHolder, RecipeImageAdapterListener>(list, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeImageViewHolder =
        RecipeImageViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false),
            listener = listener
        )

    class RecipeImageViewHolder(
        private val view: View,
        override var listener: RecipeImageAdapterListener
    ) : BaseAdapter.BaseViewHolder<RecipeImageItem, RecipeImageAdapterListener>(view, listener) {

        override fun bindHolder(item: RecipeImageItem) {
            view.apply {
                Glide.with(this)
                    .load(item.url)
                    .apply(
                        RequestOptions()
                            .dontTransform()
                            .error(R.drawable.ic_placeholder)
                    )
                    .into(ivImageItem)

                setOnClickListener {
                    showDialog(item.url)
                }

            }
        }

        private fun showDialog(url: String) {
            MaterialDialog(view.context)
                .title(R.string.activity_recipe_download_dialog_title)
                .negativeButton(R.string.activity_recipe_download_dialog_no)
                .positiveButton(R.string.activity_recipe_download_dialog_yes) {
                    listener.checkPermissionsAndSaveBitmapToGallery(url)
                }
                .show()
        }

    }

}
