package com.habibfr.myworkmanager

import com.squareup.moshi.Json

data class ResponseWeather(

	@Json(name="visibility")
	val visibility: Int? = null,

	@Json(name="timezone")
	val timezone: Int? = null,

	@Json(name="main")
	val main: Main,

	@Json(name="clouds")
	val clouds: Clouds? = null,

	@Json(name="sys")
	val sys: Sys? = null,

	@Json(name="dt")
	val dt: Int? = null,

	@Json(name="coord")
	val coord: Coord? = null,

	@Json(name="weather")
	val weather: List<WeatherItem?>? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="cod")
	val cod: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="base")
	val base: String? = null,

	@Json(name="wind")
	val wind: Wind? = null
)

data class WeatherItem(

	@Json(name="icon")
	val icon: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="main")
	val main: String? = null,

	@Json(name="id")
	val id: Int? = null
)

data class Main(

	@Json(name="temp")
	val temp: Double,

	@Json(name="temp_min")
	val tempMin: Any? = null,

	@Json(name="humidity")
	val humidity: Int? = null,

	@Json(name="pressure")
	val pressure: Int? = null,

	@Json(name="feels_like")
	val feelsLike: Any? = null,

	@Json(name="temp_max")
	val tempMax: Any? = null
)

data class Sys(

	@Json(name="country")
	val country: String? = null,

	@Json(name="sunrise")
	val sunrise: Int? = null,

	@Json(name="sunset")
	val sunset: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="type")
	val type: Int? = null
)

data class Clouds(

	@Json(name="all")
	val all: Int? = null
)

data class Coord(

	@Json(name="lon")
	val lon: Any? = null,

	@Json(name="lat")
	val lat: Any? = null
)

data class Wind(

	@Json(name="deg")
	val deg: Int? = null,

	@Json(name="speed")
	val speed: Any? = null,

	@Json(name="gust")
	val gust: Any? = null
)
