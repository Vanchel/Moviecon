package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Movie

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB краткие сведения о фильме.
 *
 * @property adult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property backdropPath ссылка на обложку фильма
 * @property genreIds список идентификаторов жанров, к которым относится фильм
 * @property id идентификатор записи о фильме
 * @property originalLanguage исходный язык фильма
 * @property originalTitle исходное название фильма
 * @property overview краткое описание фильма
 * @property posterPath ссылка на постер фильма
 * @property releaseDate дата выхода фильма
 * @property title название фильма
 * @property video флаг, показывающий, доступен ли фильм в виде видеозаписи на сервисе TMDB
 * @property voteAverage средняя оценка фильма
 * @property voteCount количество пользовательских оценок
 * @property popularity популярность фильма
 * @property mediaType тип медиа
 */
data class MovieResponse(
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "genre_ids") val genreIds: List<Int>? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "video") val video: Boolean? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "media_type") val mediaType: String? = null
) : Transformable<Movie> {

    override fun transform() = Movie(
        id = id ?: -1,
        isAdult = adult ?: false,
        backdropPath = backdropPath,
        genreIds = genreIds ?: listOf(),
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        popularity = popularity ?: 0.0
    )
}