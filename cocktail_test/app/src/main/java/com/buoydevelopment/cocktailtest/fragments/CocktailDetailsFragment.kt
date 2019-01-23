package com.buoydevelopment.cocktailtest.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.buoydevelopment.cocktailtest.R
import com.buoydevelopment.cocktailtest.viewmodels.CocktailViewModel
import kotlinx.android.synthetic.main.fragment_cocktail_details.view.*

class CocktailDetailsFragment : BaseFragment() {

    private var title = ""
    private var cocktailId = ""
    private lateinit var cocktailViewModel: CocktailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            title = args.getString(TITLE_KEY, "")
            cocktailId = args.getString(COCKTAIL_ID_KEY, "")
        }

        activity?.let {
            cocktailViewModel = ViewModelProviders.of(it, viewModelFactory)[CocktailViewModel::class.java]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val containerView = inflater.inflate(R.layout.fragment_cocktail_details, container, false)

        val toolbar = containerView.toolbar as Toolbar
        setUpToolbar(toolbar, title, R.drawable.ic_arrow_back_white_24dp)

        return containerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()

        cocktailViewModel.setRequestCocktailDetails(cocktailId)
    }

    private fun setObservers() {
        activity?.let { unWrappedActivity ->
            cocktailViewModel.chosenCocktailResult.observe(unWrappedActivity, Observer { cocktail ->
                if (isAdded && cocktail != null) {
                    view?.imgCocktailPicture?.let {
                        Glide.with(this)
                            .load(cocktail.photoUrl)
                            .into(it)
                    }
                }
            })

            cocktailViewModel.cocktailDetailsResult.observe(unWrappedActivity, Observer { details ->
                if (isAdded && details != null) {
                    val ingredientsBuilder = StringBuilder()

                    for (ingredient in details.ingredients) {
                        ingredientsBuilder.append("${ingredient.ingredientMeasure} ${ingredient.ingredientName}")
                        ingredientsBuilder.append("\n")
                    }

                    view?.tvIngredients?.text = ingredientsBuilder.toString()
                    view?.tvInstructions?.text = details.instructions
                }
            })
        }
    }

    companion object {
        private const val TITLE_KEY = "TITLE_KEY"
        private const val COCKTAIL_ID_KEY = "COCKTAIL_ID_KEY"

        fun newInstance(title: String, cocktailId: String) : CocktailDetailsFragment {
            val cocktailDetailsFragment = CocktailDetailsFragment()
            val args = Bundle()
            args.putString(TITLE_KEY, title)
            args.putString(COCKTAIL_ID_KEY, cocktailId)
            cocktailDetailsFragment.arguments = args

            return cocktailDetailsFragment
        }
    }
}