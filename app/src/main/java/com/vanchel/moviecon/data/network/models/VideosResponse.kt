package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о видеозаписях фильма или сериала.
 *
 * @property id идентификатор фильма или сериала
 * @property results список видеозаписей
 */
data class VideosResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "result") val results: List<VideoResponse>? = null
)