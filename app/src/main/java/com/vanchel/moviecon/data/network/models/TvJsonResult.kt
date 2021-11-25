package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о сериале.
 *
 * @property originalName исходное название сериала
 * @property originCountry страна-производитель сериала
 * @property overview краткое описание сериала
 * @property voteCount количество пользовательских оценок
 * @property firstAirDate дата выпуска пилотного эпизода сериала
 * @property backdropPath ссылка на обложку сериала
 * @property voteAverage средняя оценка сериала
 * @property genreIds список идентификаторов жанров, к которым относится сериал
 * @property posterPath ссылка на постер сериала
 * @property originalLanguage исходный язык сериала
 * @property id идентификатор записи о сериале
 * @property name название сериала
 * @property popularity популярность сериала
 * @property mediaType тип медиа
 */
data class TvJsonResult(
    @Json(name = "original_name") val originalName: String,
    @Json(name = "origin_country") val originCountry: List<String>,
    val overview: String,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "original_language") val originalLanguage: String,
    val id: Int,
    val name: String,
    val popularity: Double,
    @Json(name = "media_type") val mediaType: String?
)