package com.buoydevelopment.cocktailtest.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buoydevelopment.cocktailtest.R
import com.buoydevelopment.cocktailtest.adapters.CocktailAdapter
import com.buoydevelopment.cocktailtest.viewmodels.CocktailViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : BaseFragment() {

    private lateinit var cocktailViewModel: CocktailViewModel
    private var adapter : CocktailAdapter ?= null
    private var listener: CocktailAdapter.ICocktailAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            cocktailViewModel = ViewModelProviders.of(it, viewModelFactory)[CocktailViewModel::class.java]
            adapter = CocktailAdapter(it, cocktailViewModel, listener)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val containerView = inflater.inflate(R.layout.fragment_main, container, false)

        containerView.rvItems.setHasFixedSize(true)
        containerView.rvItems.adapter = adapter
        val toolbar = containerView.toolbar as Toolbar
        setUpToolbar(toolbar, getString(R.string.title_main))

        return containerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()

        cocktailViewModel.setRequestCocktails(true)
    }

    private fun setObservers() {
        activity?.let { unWrappedActivity ->
            cocktailViewModel.listOfCocktailsResult.observe(unWrappedActivity, Observer { cocktailsResult ->
                if (isAdded) {
                    if (cocktailsResult != null) {
                        //progressBar.visibility = View.GONE

                        if (cocktailsResult.any()) {
                            adapter?.notifyDataSetChanged()
                            view?.rvItems?.visibility = View.VISIBLE
                            //tvMessage.visibility = View.GONE
                        } else {
                            view?.rvItems?.visibility = View.GONE
                            //tvMessage.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as CocktailAdapter.ICocktailAdapter
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}