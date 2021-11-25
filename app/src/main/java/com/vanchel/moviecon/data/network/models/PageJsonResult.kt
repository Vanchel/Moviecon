package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученный с сервиса TMDB список данных типа [T] в виде страницы.
 *
 * @param T тип данных, хранящихся в списке
 * @property page порядковый номер страницы данных
 * @property dates даты, в границах которых выбираются результаты
 * @property results список результатов типа [T]
 * @property totalPages общее количество страниц с результатами
 * @property totalResults общее количество результатов
 */
data class PageJsonResult<T>(
    val page: Int,
    val dates: DatesJsonResult?,
    val results: List<T>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)