package com.buoydevelopment.cocktailtest.models

data class CocktailDetails(
    var id: String = "",
    var name: String = "",
    var photoUrl: String = "",
    var instructions: String = "",
    var ingredients: ArrayList<CocktailIngredient> = arrayListOf()
)

data class CocktailIngredient(
    var ingredientName: String = "",
    var ingredientMeasure: String = ""
)
