package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.CinematicCreditsJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [CinematicCreditsJsonResult] в список объектов типа [Cast].
 *
 * @property cinematicCastConverter конвертер из типа [CinematicCastJsonResult] в тип [Cast]
 */
class CastToCinematicCreditsJsonResultConverter @Inject constructor(
    private val cinematicCastConverter: EntityConverter<Cast, CinematicCastJsonResult>
) : EntityConverter<List<Cast>, CinematicCreditsJsonResult> {
    override fun toDomainModel(dto: CinematicCreditsJsonResult): List<Cast> {
        return dto.cast.map(cinematicCastConverter::toDomainModel)
    }
}