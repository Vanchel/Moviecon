package com.vanchel.moviecon.data.network.models

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
    val id: Int?,
    val cast: List<MovieCreditsJsonResult>,
    val crew: List<MovieCreditsJsonResult>
)