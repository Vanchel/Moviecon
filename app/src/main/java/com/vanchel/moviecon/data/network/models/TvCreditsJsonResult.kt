package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о роли человека в съемках сериала.
 *
 * @property creditId идентификатор записи об участии человека в съемках сериала
 * @property originalName исходное название сериала
 * @property id идентификатор записи о роли человека в сериале
 * @property genreIds список идентификаторов жанров, к которым относится сериал
 * @property character имя персонажа, если человек - актер
 * @property job должность члена съемочной группы, если человек - не актер
 * @property department группа внутри съемочной группы, к которой относится человек
 * @property name название сериала
 * @property posterPath ссылка на постер сериала
 * @property voteCount количество пользовательских оценок
 * @property voteAverage средняя пользовательская оценка
 * @property popularity популярность сериала
 * @property episodeCount количество эпизодов
 * @property originalLanguage  исходный язык сериала
 * @property firstAirDate дата выхода пилотного выпуска сериала
 * @property backdropPath ссылка на обложку сериала
 * @property overview краткий обзор сериала
 * @property originCountry страна, в которой снят фильм
 */
data class TvCreditsJsonResult(
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "original_name") val originalName: String,
    val id: Int,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    val character: String?,
    val job: String?,
    val department: String?,
    val name: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "vote_average") val voteAverage: Double,
    val popularity: Double,
    @Json(name = "episode_count") val episodeCount: Int?,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    val overview: String,
    @Json(name = "origin_country") val originCountry: List<String>
)