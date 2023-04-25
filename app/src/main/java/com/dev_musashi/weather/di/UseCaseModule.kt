package com.dev_musashi.weather.di

import com.dev_musashi.weather.data.repository.WeatherRepositoryImpl
import com.dev_musashi.weather.domain.usecase.GetCurrentWeather
import com.dev_musashi.weather.domain.usecase.GetTodayWeather
import com.dev_musashi.weather.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: WeatherRepositoryImpl) : UseCases {
        return UseCases(
            getTodayWeather = GetTodayWeather(repository),
            getCurrentWeather = GetCurrentWeather(repository)
        )
    }

}