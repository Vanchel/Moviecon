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
data class CinematicCrewResponse(
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "gender") val gender: Int? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "known_for_department") val knownForDepartment: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "original_name") val originalName: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "profile_path") val profilePath: String? = null,
    @Json(name = "credit_id") val creditId: String? = null,
    @Json(name = "department") val department: String? = null,
    @Json(name = "job") val job: String? = null
)