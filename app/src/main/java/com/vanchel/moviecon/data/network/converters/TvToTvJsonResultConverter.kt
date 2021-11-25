package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.TvJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.util.DATE_FORMAT_DEFAULT
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [TvJsonResult] в тип [Tv].
 */
class TvToTvJsonResultConverter @Inject constructor()
    : EntityConverter<Tv, TvJsonResult> {
    override fun toDomainModel(dto: TvJsonResult): Tv {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())
        val firstAirDate = if (!dto.firstAirDate.isNullOrBlank()) {
            dto.firstAirDate.let(dateFormat::parse)
        } else {
            null
        }

        return Tv(
            id = dto.id,
            name = dto.name,
            popularity = dto.popularity,
            originalLanguage = dto.originalLanguage,
            originalName = dto.originalName,
            originalCountry = dto.originCountry,
            overview = dto.overview,
            voteCount = dto.voteCount,
            firstAirDate = firstAirDate,
            backdropPath = dto.backdropPath,
            voteAverage = dto.voteAverage,
            genreIds = dto.genreIds,
            posterPath = dto.posterPath
        )
    }
}