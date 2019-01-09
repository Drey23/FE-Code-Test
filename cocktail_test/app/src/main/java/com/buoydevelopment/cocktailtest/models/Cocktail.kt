package com.buoydevelopment.cocktailtest.models

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("strDrink")
    var cocktailName: String = "",

    @SerializedName("strDrinkThumb")
    var photoUrl: String = "",

    @SerializedName("idDrink")
    var cocktailId: String = ""
)