package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Video

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
data class VideoResponse(
    @Json(name = "iso_639_1") val iso639_1: String? = null,
    @Json(name = "iso_3166_1") val iso3166_1: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "key") val key: String? = null,
    @Json(name = "site") val site: String? = null,
    @Json(name = "size") val size: Int? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "official") val official: Boolean? = null,
    @Json(name = "published_at") val publishedAt: String? = null,
    @Json(name = "id") val id: String? = null
) : Transformable<Video> {

    override fun transform() = Video(
        id = id ?: "",
        name = name ?: "",
        key = key ?: "",
        isOfficial = official ?: false
    )
}