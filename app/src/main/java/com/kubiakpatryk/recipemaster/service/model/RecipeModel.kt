package com.kubiakpatryk.recipemaster.service.model

import android.os.Parcel
import android.os.Parcelable

data class RecipeModel(
    val title: String?,
    val description: String?,
    val ingredients: List<String>?,
    val preparing: List<String>?,
    val imgs: List<String>?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeStringList(ingredients)
        parcel.writeStringList(preparing)
        parcel.writeStringList(imgs)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RecipeModel> {

        override fun createFromParcel(parcel: Parcel): RecipeModel {
            return RecipeModel(parcel)
        }

        override fun newArray(size: Int): Array<RecipeModel?> {
            return arrayOfNulls(size)
        }

    }

}
