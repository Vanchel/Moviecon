package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB подробные сведения о человеке.
 *
 * @property adult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property alsoKnownAs список альтернативных имен человека
 * @property biography краткая биография человека
 * @property birthday день рождения человека
 * @property deathDay день смерти человека
 * @property gender пол человека
 * @property homepage ссылка на собственную страницу человека
 * @property id идентификатор записи о человеке
 * @property imdbId идентификатор человека на сервисе IMDb
 * @property knownForDepartment область деятельности, в которой известен человек
 * @property name имя человека
 * @property placeOfBirth место рождения человека
 * @property popularity популярность человека
 * @property profilePath ссылка на аватар
 * @property movieCredits записи об участии человека в съемках фильмов
 * @property tvCredits записи об участии человека в съемках сериалов
 * @property externalIds идентификаторы человека на сторонних ресурсах
 */
data class PersonDetailsJsonResult(
    val adult: Boolean,
    @Json(name = "also_known_as") val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String?,
    @Json(name = "deathday") val deathDay: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "known_for_department") val knownForDepartment: String,
    val name: String,
    @Json(name = "place_of_birth") val placeOfBirth: String?,
    val popularity: Double,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "movie_credits") val movieCredits: PersonMovieCreditsJsonResult?,
    @Json(name = "tv_credits") val tvCredits: PersonTvCreditsJsonResult?,
    @Json(name = "external_ids") val externalIds: ExternalIdsJsonResult?
)