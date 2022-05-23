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
data class ProductionCountryResponse(
    @Json(name = "iso_3166_1") val iso3166_1: String? = null,
    @Json(name = "name") val name: String? = null
)