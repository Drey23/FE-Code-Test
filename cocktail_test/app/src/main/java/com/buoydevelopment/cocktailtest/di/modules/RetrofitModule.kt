package com.buoydevelopment.cocktailtest.di.modules

import com.buoydevelopment.cocktailtest.common.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

//            val url = originalHttpUrl.newBuilder()
//                    .addQueryParameter("username", "demo")
//                    .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .url(originalHttpUrl)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return httpClient.build()
    }
}