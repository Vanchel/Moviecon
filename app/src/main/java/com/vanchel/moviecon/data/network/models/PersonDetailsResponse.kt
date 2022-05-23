package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Gender
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.util.extensions.defaultFormatDateOrNull

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
data class PersonDetailsResponse(
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "also_known_as") val alsoKnownAs: List<String>? = null,
    @Json(name = "biography") val biography: String? = null,
    @Json(name = "birthday") val birthday: String? = null,
    @Json(name = "deathday") val deathDay: String? = null,
    @Json(name = "gender") val gender: Int? = null,
    @Json(name = "homepage") val homepage: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "imdb_id") val imdbId: String? = null,
    @Json(name = "known_for_department") val knownForDepartment: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "place_of_birth") val placeOfBirth: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "profile_path") val profilePath: String? = null,
    @Json(name = "movie_credits") val movieCredits: PersonMovieCreditsJsonResult? = null,
    @Json(name = "tv_credits") val tvCredits: PersonTvCreditsResponse? = null,
    @Json(name = "external_ids") val externalIds: ExternalIdsResponse? = null
) : Transformable<PersonDetails> {

    override fun transform() = PersonDetails(
        id = id ?: -1,
        isAdult = adult ?: false,
        alsoKnownAs = alsoKnownAs ?: listOf(),
        biography = biography ?: "",
        birthday = birthday?.defaultFormatDateOrNull,
        deathDay = deathDay?.defaultFormatDateOrNull,
        gender = Gender.byValue(gender),
        homepage = homepage,
        name = name ?: "",
        placeOfBirth = placeOfBirth,
        popularity = popularity ?: 0.0,
        profilePath = profilePath,
        credits = movieCredits?.cast?.map(MovieCreditsResponse::transform).orEmpty() +
                tvCredits?.cast?.map(TvCreditsResponse::transform).orEmpty(),
        instagramId = externalIds?.instagramId
    )
}