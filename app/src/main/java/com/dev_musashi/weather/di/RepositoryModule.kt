package com.dev_musashi.weather.di

import com.dev_musashi.weather.data.remote.WeatherApi
import com.dev_musashi.weather.data.repository.WeatherRepositoryImpl
import com.dev_musashi.weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        weatherApi: WeatherApi
    ) : WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

}