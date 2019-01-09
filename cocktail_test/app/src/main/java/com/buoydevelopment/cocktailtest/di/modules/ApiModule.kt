package com.buoydevelopment.cocktailtest.di.modules

import com.buoydevelopment.cocktailtest.api.CocktailsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideCocktailApi(retrofit: Retrofit): CocktailsApi {
        return retrofit.create(CocktailsApi::class.java)
    }
}