package com.kubiakpatryk.recipemaster.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.snackbar.Snackbar
import com.kubiakpatryk.recipemaster.R
import com.kubiakpatryk.recipemaster.ui.base.BaseActivity
import com.kubiakpatryk.recipemaster.ui.recipe.RecipeActivity
import com.kubiakpatryk.recipemaster.util.gone
import com.kubiakpatryk.recipemaster.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException


class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {

    override val layoutResId: Int = R.layout.activity_main

    private val callbackManager = CallbackManager.Factory.create()

    private var fullName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {

                override fun onSuccess(result: LoginResult?) {
                    result?.let {
                        getUserFullName(it.accessToken)
                    }
                }

                override fun onCancel() {
                    showAnnouncement(R.string.activity_main_login_cancel)
                }

                override fun onError(error: FacebookException?) {
                    fabMainMenu.close(true)
                    showAnnouncement(R.string.activity_main_login_error)
                }

            })

        fabMainRecipe.setOnClickListener {
            AccessToken.getCurrentAccessToken().let {
                if (it != null && !it.isExpired && fullName != null) {
                    startActivity(
                        RecipeActivity.newIntent(this, presenter.recipeModel, fullName ?: "")
                    )
                } else {
                    fabMainMenu.close(true)
                    showAnnouncement(R.string.activity_main_user_log_in_first)
                }
            }
        }

        fabMainFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, PERMISSIONS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
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

    override fun showAnnouncement(@StringRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Snackbar.make(clSnackBar, resId, Snackbar.LENGTH_SHORT).apply {
                view.elevation = 0f
            }.show()
        } else {
            Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
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

    private fun getUserFullName(token: AccessToken) {
        GraphRequest.newMeRequest(token) { jsonObject, _ ->
            try {
                fullName = jsonObject.getString(NAME)
                fabMainMenu.close(true)
                showAnnouncement(R.string.activity_main_logged_in_as, fullName ?: "")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }.executeAsync()
    }

    private fun showAnnouncement(@StringRes resId: Int, arg: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Snackbar.make(clSnackBar, String.format(getString(resId, arg)), Snackbar.LENGTH_SHORT).apply {
                view.elevation = 0f
            }.show()
        } else {
            Toast.makeText(this, String.format(getString(resId, arg)), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        private const val NAME = "name"
        private val PERMISSIONS = mutableListOf("public_profile", "user_friends")
    }

}
