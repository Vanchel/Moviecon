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
data class TvEpisodeResponse(
    @Json(name = "air_date") val airDate: String? = null,
    @Json(name = "episode_number") val episodeNumber: Int? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "production_code") val productionCode: String? = null,
    @Json(name = "season_number") val seasonNumber: Int? = null,
    @Json(name = "still_path") val stillPath: String? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null
)