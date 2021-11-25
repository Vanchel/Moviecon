package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные об одном человеке из съемочной группы
 * фильма или сериала.
 *
 * @property adult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property gender пол
 * @property id идентификатор человека
 * @property knownForDepartment роль человека в процессе съемок
 * @property name имя
 * @property originalName имя без перевода на язык, используемый в запросе
 * @property popularity популярность человека
 * @property profilePath ссылка на аватар
 * @property creditId идентификатор записи обо всей съемочной группе
 * @property department группа творческого коллектива
 * @property job роль человека в группе
 */
data class CinematicCrewJsonResult(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,
    @Json(name = "known_for_department") val knownForDepartment: String,
    val name: String,
    @Json(name = "original_name") val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "credit_id") val creditId: String,
    val department: String,
    val job: String
)