package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.TvCreditsJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.CinematicType
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.util.DATE_FORMAT_DEFAULT
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [TvCreditsJsonResult] в тип [Credit].
 */
class CreditToTvCreditsJsonResultConverter @Inject constructor() :
    EntityConverter<Credit, TvCreditsJsonResult> {
    override fun toDomainModel(dto: TvCreditsJsonResult): Credit {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())
        val date = if (!dto.firstAirDate.isNullOrBlank()) {
            dto.firstAirDate.let(dateFormat::parse)
        } else {
            null
        }

        return Credit(
            id = dto.id,
            type = CinematicType.TV,
            isAdult = false,
            backdropPath = dto.backdropPath,
            genreIds = dto.genreIds,
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalName,
            overview = dto.overview,
            title = dto.name,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            popularity = dto.popularity,
            role = dto.character ?: dto.job,
            date = date
        )
    }
}