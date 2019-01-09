package com.buoydevelopment.cocktailtest.di.modules

import com.buoydevelopment.cocktailtest.application.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApplication(): App {
        return app
    }
}