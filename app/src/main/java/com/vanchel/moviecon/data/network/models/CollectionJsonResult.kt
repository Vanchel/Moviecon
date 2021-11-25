package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о коллекции.
 *
 * @property id идентификатор коллекции
 * @property name имя коллекции
 * @property posterPath ссылка на постер
 * @property backdropPath ссылка на обложку
 */
data class CollectionJsonResult(
    val id: Int,
    val name: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "backdrop_path") val backdropPath: String
)