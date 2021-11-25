package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о стриминговом сервисе.
 *
 * @property name название сервиса
 * @property id идентификатор записи о сервисе
 * @property logoPath ссылка на логотип
 * @property originCountry страна-разработчик сервиса
 */
data class TvNetworkJsonResult(
    val name: String,
    val id: Int,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String
)