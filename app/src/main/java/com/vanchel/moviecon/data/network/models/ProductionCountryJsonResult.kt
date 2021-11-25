package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о стране, в которой
 * действует продюсерская компания.
 *
 * @property iso3166_1 код страны в формате iso 3166-1
 * @property name название страны
 */
data class ProductionCountryJsonResult(
    @Json(name = "iso_3166_1") val iso3166_1: String,
    val name: String
)