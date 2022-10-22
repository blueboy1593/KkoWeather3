package com.kko.weather.data.repository

import com.kko.weather.data.mappers.toWeatherInfo
import com.kko.weather.data.remote.WeatherAPI
import com.kko.weather.domain.repository.WeatherRepository
import com.kko.weather.domain.util.Resource
import com.kko.weather.domain.weather.WeatherInfo
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherAPI: WeatherAPI
): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = weatherAPI.getWeatherData(
                    lat, long
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "What's the error?")
        }
    }
}