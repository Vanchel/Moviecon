package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Gender

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные об одном актере из актерского состава
 * фильма или сериала.
 *
 * @property adult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property gender пол
 * @property id идентификатор актера
 * @property knownForDepartment роль актера в процессе съемок
 * @property name имя
 * @property originalName имя без перевода на язык, используемый в запросе
 * @property popularity популярность актера
 * @property profilePath ссылка на аватар
 * @property castId идентификатор записи об актере в разрезе конкретной картины
 * @property character персонаж, которого актер сыграл в указанной картине
 * @property creditId идентификатор записи о полном составе съемочной группы
 * @property order порядковый номер актера
 */
data class CinematicCastResponse(
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "gender") val gender: Int? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "known_for_department") val knownForDepartment: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "original_name") val originalName: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "profile_path") val profilePath: String? = null,
    @Json(name = "cast_id") val castId: Int? = null,
    @Json(name = "character") val character: String? = null,
    @Json(name = "credit_id") val creditId: String? = null,
    @Json(name = "order") val order: Int? = null
) : Transformable<Cast> {

    override fun transform() = Cast(
        id = id ?: -1,
        isAdult = adult ?: false,
        gender = Gender.byValue(gender),
        knownForDepartment = knownForDepartment ?: "",
        name = name ?: "",
        popularity = popularity ?: 0.0,
        profilePath = profilePath,
        castId = castId,
        character = character ?: "",
        creditId = creditId ?: "",
        order = order ?: -1
    )
}