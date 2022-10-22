package com.kko.weather.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kko.weather.R
import com.kko.weather.presentation.WeatherState
import kotlin.math.abs
import kotlin.math.round

enum class CompareCondition {
    NORMAL,
    HOT,
    COLD
}

@Composable
fun WeatherCompareCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.weatherDataPerDay?.let { weatherDataPerDay ->
        val todayWeatherData = weatherDataPerDay[0]
        val tomorrowWeatherData = weatherDataPerDay[1]
        val todayTemperatureSum = todayWeatherData?.fold(0.0) { total, weatherData ->
            total + weatherData.temperatureCelsius
        }
        val todayTemperatureAvarage = round(todayTemperatureSum?.div(todayWeatherData.size)!! * 100) / 100
        val tomorrowTemperatureSum = tomorrowWeatherData?.fold(0.0) { total, weatherData ->
            total + weatherData.temperatureCelsius
        }
        val tomorrowTemperatureAvarage = round(tomorrowTemperatureSum?.div(tomorrowWeatherData.size)!! * 100) / 100
        val tomorrowMinusToday = tomorrowTemperatureAvarage - todayTemperatureAvarage

        val compareCondition: CompareCondition
        val painterResource: Int
        if (abs(tomorrowMinusToday) < 1) {
            compareCondition = CompareCondition.NORMAL
            painterResource = R.drawable.sun_normal
        } else if (tomorrowMinusToday > 0) {
            compareCondition = CompareCondition.HOT
            painterResource = R.drawable.sun_hot
        } else {
            compareCondition = CompareCondition.COLD
            painterResource = R.drawable.sun_cold
        }
        val compareConditionText = when (compareCondition) {
            CompareCondition.NORMAL -> "오늘과 내일은 비슷해요~"
            CompareCondition.HOT -> "오늘보다 내일 더워요!! ;;"
            CompareCondition.COLD -> "오늘보다 내일 추워요 ㅠㅠ"
        }

        Card (
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "내일 우리는?",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 28.sp,
                    color = Color.White
                )
                Image(
                    painter = painterResource(painterResource),
                    contentDescription = null,
                    modifier = Modifier.width(180.dp)
                )
                Text(
                    text = "오늘 평균 온도: ${todayTemperatureAvarage}ºC, 내일 평균 온도: ${tomorrowTemperatureAvarage}ºC",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = compareConditionText,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}