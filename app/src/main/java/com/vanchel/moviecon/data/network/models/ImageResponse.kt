package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Image

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные об изображении.
 *
 * @property aspectRatio соотношение сторон изображения
 * @property filePath путь к файлу
 * @property height высота
 * @property iso639_1 язык
 * @property voteAverage средняя оценка пользователей
 * @property voteCount количество пользовательских оценок
 * @property width ширина
 */
data class ImageResponse(
    @Json(name = "aspect_ratio") val aspectRatio: Double? = null,
    @Json(name = "file_path") val filePath: String? = null,
    @Json(name = "height") val height: Int? = null,
    @Json(name = "iso_639_1") val iso639_1: String? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "width") val width: Int? = null
) : Transformable<Image> {

    override fun transform() = Image(
        aspectRatio = aspectRatio ?: 1.0,
        filePath = filePath ?: "",
        width = width ?: 0,
        height = height ?: 0,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0
    )
}