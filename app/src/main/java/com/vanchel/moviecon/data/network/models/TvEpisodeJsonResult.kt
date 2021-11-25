package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения об эпизоде сериала.
 *
 * @property airDate дата показа
 * @property episodeNumber номер эпизода
 * @property id идентификатор записи об эпизоде
 * @property name название эпизода
 * @property overview краткое описание эпизода
 * @property productionCode продюсерский код
 * @property seasonNumber номер сезона
 * @property stillPath ссылка на обложку эпизода
 * @property voteAverage средняя оценка сериала
 * @property voteCount количество пользовательских оценок
 */
data class TvEpisodeJsonResult(
    @Json(name = "air_date") val airDate: String,
    @Json(name = "episode_number") val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @Json(name = "production_code") val productionCode: String,
    @Json(name = "season_number") val seasonNumber: Int,
    @Json(name = "still_path") val stillPath: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)