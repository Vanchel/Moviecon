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
data class TvNetworkResponse(
    @Json(name = "name") val name: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "logo_path") val logoPath: String? = null,
    @Json(name = "origin_country") val originCountry: String? = null
)