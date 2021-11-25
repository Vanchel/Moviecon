package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о сезоне сериала.
 *
 * @property airDate дата выпуска сезона
 * @property episodeCount число эпизодов в сезоне
 * @property id идентификатор записи о сезоне
 * @property name название сезона
 * @property overview краткое описание сезона
 * @property posterPath ссылка на постер
 * @property seasonNumber номер сезона
 */
data class TvSeasonJsonResult(
    @Json(name = "air_date") val airDate: String?,
    @Json(name = "episode_count") val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "season_number") val seasonNumber: Int
)