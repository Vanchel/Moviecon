package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

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
data class ImageJsonResult(
    @Json(name = "aspect_ratio") val aspectRatio: Double,
    @Json(name = "file_path") val filePath: String,
    val height: Int,
    @Json(name = "iso_639_1") val iso639_1: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val width: Int
)