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
data class TvCreatorJsonResult(
    val id: Int,
    @Json(name = "credit_id") val creditId: String,
    val name: String,
    val gender: Int,
    val profilePath: String?
)