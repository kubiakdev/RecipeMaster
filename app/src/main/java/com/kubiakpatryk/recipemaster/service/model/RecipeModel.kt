package com.kubiakpatryk.recipemaster.service.model

data class RecipeModel(
    val title: String?,
    val description: String?,
    val ingredients: List<String>?,
    val preparing: List<String>?,
    val imgs: List<String>?
)