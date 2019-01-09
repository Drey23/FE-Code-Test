package com.buoydevelopment.cocktailtest.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.buoydevelopment.cocktailtest.api.CocktailsApi
import com.buoydevelopment.cocktailtest.models.Cocktail
import com.buoydevelopment.cocktailtest.models.CocktailDetails
import com.buoydevelopment.cocktailtest.models.CocktailIngredient
import com.buoydevelopment.cocktailtest.models.response.CocktailResponse
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val cocktailsApi: CocktailsApi) {

    fun getFilteredCocktails(): LiveData<List<Cocktail>> {
        val cocktailsResult = MutableLiveData<List<Cocktail>>()

        cocktailsApi.getFilteredCockTails().enqueue(object : Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse>,
                response: Response<CocktailResponse>
            ) {
                val statusCode = response.code()

                if (response.body() != null) {
                    cocktailsResult.value = response.body()?.drinks
                } else {
                    cocktailsResult.value = listOf()
                }
            }

            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                // We can handle error later
                cocktailsResult.value = listOf()
            }
        })

        return cocktailsResult
    }

    fun getCocktailDetails(idDrink: String): LiveData<CocktailDetails> {
        val cocktailsResult = MutableLiveData<CocktailDetails>()

        cocktailsApi.getDrinkDetails(idDrink).enqueue(object : Callback<JsonElement> {
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                val statusCode = response.code()

                if (response.body() != null) {
                    val responseString = Gson().toJson(response.body())
                    val arrayOfDetails = JsonParser().parse(responseString).asJsonObject["drinks"].asJsonArray
                    val details = arrayOfDetails[0].asJsonObject

                    val cocktailDetails = CocktailDetails()

                    val instructions = details["strInstructions"].toString()
                    val name = details["strDrink"].toString()
                    val photoUrl = details["strDrinkThumb"].toString()

                    cocktailDetails.id = idDrink
                    cocktailDetails.name = name
                    cocktailDetails.instructions = instructions
                    cocktailDetails.photoUrl = photoUrl

                    for (i in 1..15) {
                        if (details != null) {
                            try {
                                val cocktailIngredient = CocktailIngredient()

                                if (details.has("strIngredient$i")) {
                                    val ingredient = details["strIngredient$i"]
                                    cocktailIngredient.ingredientName = ingredient.toString().trim()
                                }

                                if (details.has("strMeasure$i")) {
                                    val measure = details["strMeasure$i"].toString().trim()
                                    cocktailIngredient.ingredientMeasure = measure
                                }

                                if (cocktailIngredient.ingredientMeasure.isNotBlank() && cocktailIngredient.ingredientName.isNotBlank()) {
                                    cocktailDetails.ingredients.add(cocktailIngredient)
                                }
                            }
                            catch (e : Exception) {
                                val value = e
                                val value2 = e
                            }
                        }

                    }

                    cocktailsResult.value = cocktailDetails

                } else {
                    cocktailsResult.value = null
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // We can handle error later
                cocktailsResult.value = null
            }
        })

        return cocktailsResult
    }
}