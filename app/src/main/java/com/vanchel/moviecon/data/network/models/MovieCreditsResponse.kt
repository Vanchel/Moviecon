package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.util.extensions.defaultFormatDateOrNull

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о роли человека в съемках фильма.
 *
 * @property character имя персонажа, если человек - актер
 * @property job должность члена съемочной группы, если человек - не актер
 * @property department группа внутри съемочной группы, к которой относится человек
 * @property creditId идентификатор записи об участии человека в съемках фильма
 * @property releaseDate дата выхода фильма
 * @property voteCount количество пользовательских оценок
 * @property video флаг, показывающий, доступен ли фильм в виде видеозаписи на сервисе TMDB
 * @property adult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property voteAverage средняя пользовательская оценка
 * @property title название фильма
 * @property genreIds список идентификаторов жанров, к которым относится фильм
 * @property originalLanguage исходный язык фильма
 * @property originalTitle исходное название фильма
 * @property popularity популярность фильма
 * @property id идентификатор записи о роли человека в фильме
 * @property backdropPath ссылка на обложку фильма
 * @property overview краткий обзор фильма
 * @property posterPath ссылка на постер фильма
 */
data class MovieCreditsResponse(
    @Json(name = "character") val character: String? = null,
    @Json(name = "job") val job: String? = null,
    @Json(name = "department") val department: String? = null,
    @Json(name = "credit_id") val creditId: String? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "video") val video: Boolean? = null,
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "genre_ids") val genreIds: List<Int>? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "poster_path") val posterPath: String? = null
) : Transformable<Credit> {

    override fun transform() = Credit(
        id = id ?: -1,
        type = CinematicType.MOVIE,
        isAdult = adult ?: false,
        backdropPath = backdropPath,
        genreIds = genreIds ?: listOf(),
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        popularity = popularity ?: 0.0,
        role = character ?: job ?: "",
        date = releaseDate?.defaultFormatDateOrNull
    )
}