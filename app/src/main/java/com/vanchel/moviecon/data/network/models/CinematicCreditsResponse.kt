package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Cast

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные о съемочной группе фильма или сериала.
 *
 * @property id идентификатор фильма или сериала
 * @property cast список актеров
 * @property crew список представителей съемочной группы за исключением актеров
 */
data class CinematicCreditsResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "cast") val cast: List<CinematicCastResponse>? = null,
    @Json(name = "crew") val crew: List<CinematicCrewResponse>? = null
) : Transformable<List<Cast>> {

    override fun transform() = cast?.map(CinematicCastResponse::transform) ?: listOf()
}