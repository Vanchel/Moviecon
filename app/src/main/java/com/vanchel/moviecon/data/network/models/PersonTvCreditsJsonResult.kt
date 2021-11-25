package com.vanchel.moviecon.data.network.models

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB сведения об участии человека в съемках сериала.
 *
 * @property id идентификатор записи об участии человека в съемках сериала
 * @property cast список записей об участии человека в качестве актера
 * @property crew список записей об участии человека в качестве члена съемочной группы
 */
data class PersonTvCreditsJsonResult(
    val id: Int?,
    val cast: List<TvCreditsJsonResult>,
    val crew: List<TvCreditsJsonResult>
)