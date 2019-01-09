package com.buoydevelopment.cocktailtest.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.buoydevelopment.cocktailtest.R
import com.buoydevelopment.cocktailtest.application.App
import com.buoydevelopment.cocktailtest.viewmodels.ViewModelFactory
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)
    }

    fun replaceFragment(fragment: Fragment, containerName: Int, addToBackStack: Boolean = false) {
        if (!isFinishing) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(containerName, fragment)

            if (addToBackStack) {
                transaction.addToBackStack(null)
            }

            transaction.commit()
            currentFragment = fragment
        }
    }

    fun addFragment(
        fragment: Fragment,
        containerName: Int,
        addToBackStack: Boolean = false,
        animated: Boolean = false
    ) {
        if (!isFinishing) {
            val transaction = supportFragmentManager.beginTransaction()

            if (animated) {
                transaction.setCustomAnimations(
                    R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit
                )
            }

            transaction.add(containerName, fragment)

            if (addToBackStack) {
                transaction.addToBackStack(null)
            }

            transaction.commit()
            currentFragment = fragment
        }
    }

    fun getCurrentFragment(): Fragment? {
        return currentFragment
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.currentFragment = supportFragmentManager.findFragmentById(R.id.base_container)
    }

}