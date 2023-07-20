package com.example.flowmvvmexample.di

import com.example.flowmvvmexample.data.remote.ApiService
import com.example.flowmvvmexample.data.repository.RickAndMortyRepository
import com.example.flowmvvmexample.common.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val BASE_URL = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): RickAndMortyRepository {
        return RickAndMortyRepository(apiService)
    }
}