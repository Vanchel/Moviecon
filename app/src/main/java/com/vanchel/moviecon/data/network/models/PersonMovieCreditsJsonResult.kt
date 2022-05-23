package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения об участии человека в съемках фильма.
 *
 * @property id идентификатор записи об участии человека в съемках фильма
 * @property cast список записей об участии человека в качестве актера
 * @property crew список записей об участии человека в качестве члена съемочной группы
 */
data class PersonMovieCreditsJsonResult(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "cast") val cast: List<MovieCreditsResponse>? = null,
    @Json(name = "crew") val crew: List<MovieCreditsResponse>? = null
)