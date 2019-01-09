package com.buoydevelopment.cocktailtest.application

import android.app.Application
import com.buoydevelopment.cocktailtest.di.AppComponent
import com.buoydevelopment.cocktailtest.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var app: App
            private set

        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        appComponent = DaggerAppComponent
            .builder()
            .build()

    }
}