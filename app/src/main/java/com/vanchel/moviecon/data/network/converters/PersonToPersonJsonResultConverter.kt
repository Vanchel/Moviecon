package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.PersonJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Person
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [PersonJsonResult] в тип [Person].
 */
class PersonToPersonJsonResultConverter @Inject constructor()
    : EntityConverter<Person, PersonJsonResult> {
    override fun toDomainModel(dto: PersonJsonResult): Person {
        return Person(
            id = dto.id,
            name = dto.name,
            isAdult = dto.adult,
            profilePath = dto.profilePath,
            popularity = dto.popularity ?: 0.0
        )
    }
}