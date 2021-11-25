package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

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
data class PersonJsonResult(
    @Json(name = "profile_path") val profilePath: String?,
    val adult: Boolean,
    val id: Int,
    val name: String,
    val popularity: Double?
)