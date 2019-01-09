package com.buoydevelopment.cocktailtest.models.response

import com.buoydevelopment.cocktailtest.models.Cocktail

data class CocktailResponse(
    val drinks: List<Cocktail> = listOf()
)