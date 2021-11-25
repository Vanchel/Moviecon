package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о видеозаписи.
 *
 * @property iso639_1 код языка в формате iso 639-1
 * @property iso3166_1 код языка в формате iso 3166-1
 * @property name название видео
 * @property key идентификатор видео на разместившем его сервисе
 * @property site название сайта, на котором размещено видео
 * @property size разрешение видео
 * @property type тип видео
 * @property official флаг, показывающий, является ли видео официальным
 * @property publishedAt дата публикации видео
 * @property id идентификатор записи о видео
 */
data class VideoJsonResult(
    @Json(name = "iso_639_1") val iso639_1: String,
    @Json(name = "iso_3166_1") val iso3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    @Json(name = "published_at") val publishedAt: String,
    val id: String
)