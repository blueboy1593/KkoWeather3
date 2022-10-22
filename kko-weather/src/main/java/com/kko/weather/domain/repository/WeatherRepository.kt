package com.kko.weather.domain.repository

import com.kko.weather.domain.util.Resource
import com.kko.weather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}