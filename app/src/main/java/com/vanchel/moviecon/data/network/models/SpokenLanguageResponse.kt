package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о языке.
 *
 * @property englishName название языка на английском
 * @property iso639_1 код языка в формате iso 639-1
 * @property name название языка
 */
data class SpokenLanguageResponse(
    @Json(name = "english_name") val englishName: String? = null,
    @Json(name = "iso_639_1") val iso639_1: String? = null,
    @Json(name = "name") val name: String? = null
)