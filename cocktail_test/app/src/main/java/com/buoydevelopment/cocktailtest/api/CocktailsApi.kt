package com.buoydevelopment.cocktailtest.api

import com.buoydevelopment.cocktailtest.models.response.CocktailResponse
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {

    @GET("/api/json/v1/1/filter.php?g=Cocktail_glass")
    fun getFilteredCockTails(): Call<CocktailResponse>

    @GET("/api/json/v1/1/lookup.php")
    fun getDrinkDetails(@Query("i") idDrink: String): Call<JsonElement>
}