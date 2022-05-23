package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о жанре картины.
 *
 * @property id идентификатор жанра
 * @property name название жанра
 */
data class GenreResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null
)