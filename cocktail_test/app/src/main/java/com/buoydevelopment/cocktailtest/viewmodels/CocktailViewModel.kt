package com.buoydevelopment.cocktailtest.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.buoydevelopment.cocktailtest.models.Cocktail
import com.buoydevelopment.cocktailtest.models.CocktailDetails
import com.buoydevelopment.cocktailtest.repositories.CocktailRepository
import javax.inject.Inject

class CocktailViewModel @Inject constructor(private val cocktailRepository: CocktailRepository) : ViewModel() {

    private val requestCocktails: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    private val requestCocktailDetails: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val chosenCocktail: MutableLiveData<Cocktail> by lazy { MutableLiveData<Cocktail>() }

    val listOfCocktailsResult: LiveData<List<Cocktail>> =
        Transformations.switchMap(requestCocktails) { requestCocktailsResult ->
            val result = MutableLiveData<List<Cocktail>>()

            if (requestCocktailsResult) {
                Transformations.switchMap(cocktailRepository.getFilteredCocktails()) {
                    result.value = it
                    result
                }
            } else {
                result.value = listOf()
                result
            }
        }

    val cocktailDetailsResult: LiveData<CocktailDetails> =
        Transformations.switchMap(requestCocktailDetails) { idDrink ->
            val result = MutableLiveData<CocktailDetails>()

            if (idDrink.isNotBlank()) {
                Transformations.switchMap(cocktailRepository.getCocktailDetails(idDrink)) {
                    result.value = it
                    result
                }
            } else {
                result.value = null
                result
            }
        }

    val chosenCocktailResult: LiveData<Cocktail> =
        Transformations.switchMap(chosenCocktail) { cocktail ->
            val result = MutableLiveData<Cocktail>()
            result.value = cocktail
            result
        }

    fun getCocktailDetails(idDrink: String): LiveData<CocktailDetails> {
        val result = MutableLiveData<CocktailDetails>()

        return Transformations.switchMap(cocktailRepository.getCocktailDetails(idDrink)) {
            result.value = it
            result
        }
    }

    fun setRequestCocktails(value: Boolean) {
        requestCocktails.value = value
    }

    fun setRequestCocktailDetails(value: String) {
        requestCocktailDetails.value = value
    }

    fun setChosenCocktail(value: Cocktail) {
        chosenCocktail.value = value
    }
}