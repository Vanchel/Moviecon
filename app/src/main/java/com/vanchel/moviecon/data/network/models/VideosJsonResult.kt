package com.vanchel.moviecon.data.network.models

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о видеозаписях фильма или сериала.
 *
 * @property id идентификатор фильма или сериала
 * @property results список видеозаписей
 */
data class VideosJsonResult(
    val id: Int?,
    val results: List<VideoJsonResult>
)