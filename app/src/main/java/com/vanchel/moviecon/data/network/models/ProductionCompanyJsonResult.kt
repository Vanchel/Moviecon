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
data class ProductionCompanyJsonResult(
    val id: Int,
    @Json(name = "logo_path") val logoPath: String?,
    val name: String,
    @Json(name = "origin_country") val originCountry: String
)