package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения о продюсерской компании.
 *
 * @property id идентификатор записи о продюсерской компании
 * @property logoPath ссылка на логотип
 * @property name название компании
 * @property originCountry страна, в которой компания ведет основную деятельность
 */
data class ProductionCompanyResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "logo_path") val logoPath: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "origin_country") val originCountry: String? = null
)