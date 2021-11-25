package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.MovieCreditsJsonResult
import com.vanchel.moviecon.data.network.models.PersonDetailsJsonResult
import com.vanchel.moviecon.data.network.models.TvCreditsJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.util.DATE_FORMAT_DEFAULT
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [PersonDetailsJsonResult] в тип [PersonDetails].
 *
 * @property movieCreditsConverter конвертер из типа [MovieCreditsJsonResult] в тип [Credit]
 * @property tvCreditsConverter конвертер из типа [TvCreditsJsonResult] в тип [PersonDetails]
 */
class PersonDetailsToPersonDetailsJsonResultConverter @Inject constructor(
    private val movieCreditsConverter: EntityConverter<Credit, MovieCreditsJsonResult>,
    private val tvCreditsConverter: EntityConverter<Credit, TvCreditsJsonResult>
) : EntityConverter<PersonDetails, PersonDetailsJsonResult> {
    override fun toDomainModel(dto: PersonDetailsJsonResult): PersonDetails {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())
        val birthDay = if (!dto.birthday.isNullOrBlank()) {
            dto.birthday.let(dateFormat::parse)
        } else {
            null
        }
        val deathDay = if (!dto.birthday.isNullOrBlank()) {
            dto.birthday.let(dateFormat::parse)
        } else {
            null
        }

        val movieCredits = dto.movieCredits?.cast
            ?.map(movieCreditsConverter::toDomainModel) ?: emptyList()
        val tvCredits = dto.tvCredits?.cast
            ?.map(tvCreditsConverter::toDomainModel) ?: emptyList()

        return PersonDetails(
            id = dto.id,
            isAdult = dto.adult,
            alsoKnownAs = dto.alsoKnownAs,
            biography = dto.biography,
            birthday = birthDay,
            deathDay = deathDay,
            gender = dto.gender,
            homepage = dto.homepage,
            name = dto.name,
            placeOfBirth = dto.placeOfBirth,
            popularity = dto.popularity,
            profilePath = dto.profilePath,
            credits = movieCredits + tvCredits,
            instagramId = dto.externalIds?.instagramId
        )
    }
}