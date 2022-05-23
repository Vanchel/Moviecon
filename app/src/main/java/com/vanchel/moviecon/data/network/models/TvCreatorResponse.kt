package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о создателе сериала.
 *
 * @property id идентификатор записи о создателе сериала
 * @property creditId идентификатор записи о создателе сериала в съемочной группе
 * @property name имя
 * @property gender пол
 * @property profilePath ссылка на аватар
 */
data class TvCreatorResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "credit_id") val creditId: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "gender") val gender: Int? = null,
    @Json(name = "profile_path") val profilePath: String? = null
)