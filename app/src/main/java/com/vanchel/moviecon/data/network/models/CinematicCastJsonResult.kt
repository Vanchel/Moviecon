package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

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
data class CinematicCastJsonResult(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,
    @Json(name = "known_for_department") val knownForDepartment: String,
    val name: String,
    @Json(name = "original_name") val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "cast_id") val castId: Int?,
    val character: String,
    @Json(name = "credit_id") val creditId: String,
    val order: Int
)