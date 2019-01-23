package com.buoydevelopment.cocktailtest.adapters

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.buoydevelopment.cocktailtest.R
import com.buoydevelopment.cocktailtest.activities.BaseActivity
import com.buoydevelopment.cocktailtest.models.Cocktail
import com.buoydevelopment.cocktailtest.viewmodels.CocktailViewModel
import kotlinx.android.synthetic.main.adapter_cocktails.view.*

class CocktailAdapter(
    private val context: Context,
    private val cocktailViewModel: CocktailViewModel,
    private val listener: ICocktailAdapter? = null
) : RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cocktails = cocktailViewModel.listOfCocktailsResult.value ?: listOf()
        val item = cocktails[position]
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.adapter_cocktails, false)
        return ViewHolder(inflatedView, context, cocktailViewModel)
    }

    override fun getItemCount(): Int {
        val cocktails = cocktailViewModel.listOfCocktailsResult.value ?: listOf()
        return cocktails.size
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    class ViewHolder(
        val view: View,
        private val context: Context,
        private val cocktailViewModel: CocktailViewModel
    ) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private var cocktail: Cocktail? = null
        private var listener: ICocktailAdapter? = null
        private var dataLoaded = false
        var currentId = ""

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(cocktail: Cocktail, listener: ICocktailAdapter?) {
            this.cocktail = cocktail
            this.listener = listener

            val (cocktailName, photoUrl, cocktailId) = cocktail

            view.tvName.text = cocktailName

            Glide.with(context)
                .load(photoUrl)
                .into(view.imgThumbnail)

            //setObservers()

            if (!dataLoaded) {
                cocktailViewModel.getCocktailDetails(cocktailId).observe(context as BaseActivity, Observer {
                    if (it != null) {
                        dataLoaded = true
                        currentId = it.id
                        this.view.tvIngredientOne.text = it.ingredients[0].ingredientName
                        this.view.tvIngredientTwo.text = it.ingredients[1].ingredientName
                    }
                })
            }
        }

        override fun onClick(v: View?) {
            cocktail?.let {
                if (currentId.isNotEmpty()) {
                    listener?.cocktailTapped(currentId, it)
                }
            }
        }

        private fun setObservers() {
            cocktailViewModel.cocktailDetailsResult.observe(
                context as BaseActivity,
                Observer { details ->
                    if (details != null) {
                        dataLoaded = true
                        this.view.tvIngredientOne.text = details.ingredients[0].ingredientName
                        this.view.tvIngredientTwo.text = details.ingredients[1].ingredientName
                    }
                })
        }

    }

    interface ICocktailAdapter {
        fun cocktailTapped(cocktailId: String, cocktail: Cocktail)
    }
}