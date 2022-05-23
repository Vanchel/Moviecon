package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Person

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB краткие сведения о человеке.
 *
 * @property profilePath ссылка на аватар
 * @property adult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property id идентификатор записи о человеке
 * @property name имя человека
 * @property popularity популярность человека
 */
data class PersonResponse(
    @Json(name = "profile_path") val profilePath: String? = null,
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "popularity") val popularity: Double? = null
) : Transformable<Person> {

    override fun transform() = Person(
        id = id ?: -1,
        name = name ?: "",
        isAdult = adult ?: false,
        profilePath = profilePath ?: "",
        popularity = popularity ?: 0.0
    )
}