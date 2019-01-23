package com.buoydevelopment.cocktailtest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.View
import com.buoydevelopment.cocktailtest.activities.BaseActivity
import com.buoydevelopment.cocktailtest.application.App
import com.buoydevelopment.cocktailtest.viewmodels.ViewModelFactory
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)
    }

    fun setUpToolbar(toolbar: Toolbar?, title: String = "", homeIcon: Int = 0, onClickListener: View.OnClickListener ?= null) {
        if (toolbar != null) {
            val activity = activity

            if (activity != null && activity is BaseActivity) {
                toolbar.title = title

                if (homeIcon != 0) {
                    toolbar.navigationIcon = ContextCompat.getDrawable(activity, homeIcon)
                }

                activity.setSupportActionBar(toolbar)

                if (onClickListener != null) {
                    toolbar.setNavigationOnClickListener(onClickListener)
                } else {
                    toolbar.setNavigationOnClickListener {
                        activity.onBackPressed()
                    }
                }

            }
        }
        else {
            toolbar?.visibility = View.GONE
        }
    }
}