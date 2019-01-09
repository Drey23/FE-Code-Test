package com.buoydevelopment.cocktailtest.di

import com.buoydevelopment.cocktailtest.activities.BaseActivity
import com.buoydevelopment.cocktailtest.application.App
import com.buoydevelopment.cocktailtest.di.modules.*
import com.buoydevelopment.cocktailtest.fragments.BaseFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ApiModule::class, RetrofitModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(value: BaseActivity)
    fun inject(value: BaseFragment)
}