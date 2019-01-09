package com.buoydevelopment.cocktailtest.di.modules

import com.buoydevelopment.cocktailtest.api.CocktailsApi
import com.buoydevelopment.cocktailtest.repositories.CocktailRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideCocktailRepository(cocktailsApi: CocktailsApi): CocktailRepository {
        return CocktailRepository(cocktailsApi)
    }
}