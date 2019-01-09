package com.buoydevelopment.cocktailtest.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.buoydevelopment.cocktailtest.R
import com.buoydevelopment.cocktailtest.adapters.CocktailAdapter
import com.buoydevelopment.cocktailtest.fragments.CocktailDetailsFragment
import com.buoydevelopment.cocktailtest.fragments.MainFragment
import com.buoydevelopment.cocktailtest.models.Cocktail
import com.buoydevelopment.cocktailtest.viewmodels.CocktailViewModel

class MainActivity : BaseActivity(), CocktailAdapter.ICocktailAdapter {

    private val mainFragment = MainFragment()
    private lateinit var cocktailViewModel: CocktailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        cocktailViewModel = ViewModelProviders.of(this, viewModelFactory)[CocktailViewModel::class.java]

        replaceFragment(mainFragment, R.id.base_container)
    }

    override fun cocktailTapped(cocktail: Cocktail) {
        val (cocktailName, _, cocktailId) = cocktail
        cocktailViewModel.setChosenCocktail(cocktail)

        val cocktailDetails = CocktailDetailsFragment.newInstance(cocktailName, cocktailId)
        addFragment(cocktailDetails, R.id.base_container, true, true)
    }
}