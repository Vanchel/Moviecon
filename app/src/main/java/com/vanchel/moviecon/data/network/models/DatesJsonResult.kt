package com.vanchel.moviecon.data.network.models

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о временных рамках.
 *
 * @property maximum самая поздняя дата
 * @property minimum самая ранняя дата
 */
data class DatesJsonResult(
    val maximum: String,
    val minimum: String
)