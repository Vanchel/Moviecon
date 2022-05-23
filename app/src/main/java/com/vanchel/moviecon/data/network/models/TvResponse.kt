package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.util.extensions.defaultFormatDateOrNull

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
data class TvResponse(
    @Json(name = "original_name") val originalName: String? = null,
    @Json(name = "origin_country") val originCountry: List<String>? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "first_air_date") val firstAirDate: String? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "genre_ids") val genreIds: List<Int>? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "media_type") val mediaType: String? = null
) : Transformable<Tv> {

    override fun transform() = Tv(
        id = id ?: -1,
        name = name ?: "",
        popularity = popularity ?: 0.0,
        originalLanguage = originalLanguage ?: "",
        originalName = originalName ?: "",
        originalCountry = originCountry ?: listOf(),
        overview = overview ?: "",
        voteCount = voteCount ?: 0,
        firstAirDate = firstAirDate?.defaultFormatDateOrNull,
        backdropPath = backdropPath,
        voteAverage = voteAverage ?: 0.0,
        genreIds = genreIds ?: listOf(),
        posterPath = posterPath
    )
}