package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о временных рамках.
 *
 * @property maximum самая поздняя дата
 * @property minimum самая ранняя дата
 */
data class DatesResponse(
    @Json(name = "maximum") val maximum: String? = null,
    @Json(name = "minimum") val minimum: String? = null
)