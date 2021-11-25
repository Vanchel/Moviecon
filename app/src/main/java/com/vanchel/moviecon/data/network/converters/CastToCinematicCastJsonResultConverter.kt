package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Person

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [CinematicCastJsonResult] в тип [Cast].
 */
class CastToCinematicCastJsonResultConverter : EntityConverter<Cast, CinematicCastJsonResult> {
    override fun toDomainModel(dto: CinematicCastJsonResult) = Cast(
        id = dto.id,
        isAdult = dto.adult,
        gender = dto.gender,
        knownForDepartment = dto.knownForDepartment,
        name = dto.name,
        popularity = dto.popularity,
        profilePath = dto.profilePath,
        castId = dto.castId,
        character = dto.character,
        creditId = dto.creditId,
        order = dto.order
    )
}