package com.vanchel.moviecon.data.network.models

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о съемочной группе фильма или сериала.
 *
 * @property id идентификатор фильма или сериала
 * @property cast список актеров
 * @property crew список представителей съемочной группы за исключением актеров
 */
data class CinematicCreditsJsonResult(
    val id: Int?,
    val cast: List<CinematicCastJsonResult>,
    val crew: List<CinematicCrewJsonResult>
)