package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

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
data class MovieCreditsJsonResult(
    val character: String?,
    val job: String?,
    val department: String?,
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_count") val voteCount: Int,
    val video: Boolean,
    val adult: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    val title: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    val popularity: Double,
    val id: Int,
    @Json(name = "backdrop_path") val backdropPath: String?,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?
)