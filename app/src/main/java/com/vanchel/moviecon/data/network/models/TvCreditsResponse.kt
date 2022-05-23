package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.util.extensions.defaultFormatDateOrNull

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
data class TvCreditsResponse(
    @Json(name = "credit_id") val creditId: String? = null,
    @Json(name = "original_name") val originalName: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "genre_ids") val genreIds: List<Int>? = null,
    @Json(name = "character") val character: String? = null,
    @Json(name = "job") val job: String? = null,
    @Json(name = "department") val department: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "episode_count") val episodeCount: Int? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "first_air_date") val firstAirDate: String? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "origin_country") val originCountry: List<String>? = null
) : Transformable<Credit> {

    override fun transform() = Credit(
        id = id ?: -1,
        type = CinematicType.TV,
        isAdult = false,
        backdropPath = backdropPath,
        genreIds = genreIds ?: listOf(),
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalName ?: "",
        overview = overview ?: "",
        title = name ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        popularity = popularity ?: 0.0,
        role = character ?: job,
        date = firstAirDate?.defaultFormatDateOrNull
    )
}