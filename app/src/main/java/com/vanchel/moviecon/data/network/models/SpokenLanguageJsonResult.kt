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
data class SpokenLanguageJsonResult(
    @Json(name = "english_name") val englishName: String,
    @Json(name = "iso_639_1") val iso639_1: String,
    val name: String
)