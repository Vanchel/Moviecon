package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.*
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.TvDetails
import com.vanchel.moviecon.domain.entities.Video
import com.vanchel.moviecon.util.DATE_FORMAT_DEFAULT
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [TvDetailsJsonResult] в тип [TvDetails].
 *
 * @property videoConverter конвертер из типа [VideoJsonResult] в тип [Video]
 * @property cinematicCastConverter конвертер из типа [CinematicCastJsonResult] в тип [Cast]
 */
class TvDetailsToTvDetailsJsonResultConverter @Inject constructor(
    private val videoConverter: EntityConverter<Video, VideoJsonResult>,
    private val cinematicCastConverter: EntityConverter<Cast, CinematicCastJsonResult>
) : EntityConverter<TvDetails, TvDetailsJsonResult> {
    override fun toDomainModel(dto: TvDetailsJsonResult): TvDetails {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())
        val firstAirDate = if (!dto.firstAirDate.isNullOrBlank()) {
            dto.firstAirDate.let(dateFormat::parse)
        } else {
            null
        }
        val lastAirDate = if (!dto.lastAirDate.isNullOrBlank()) {
            dto.lastAirDate.let(dateFormat::parse)
        } else {
            null
        }

        return TvDetails(
            id = dto.id,
            name = dto.name,
            popularity = dto.popularity,
            originalName = dto.originalName,
            originalCountry = dto.originCountry,
            originalLanguage = dto.originalLanguage,
            overview = dto.overview,
            voteCount = dto.voteCount,
            firstAirDate = firstAirDate,
            lastAirDate = lastAirDate,
            numberOfEpisodes = dto.numberOfEpisodes,
            numberOfSeasons = dto.numberOfSeasons,
            backdropPath = dto.backdropPath,
            creatorNames = dto.createdBy.map(TvCreatorJsonResult::name),
            episodeRunTime = dto.episodeRunTime,
            genreNames = dto.genres.map(GenreJsonResult::name),
            homepage = dto.homepage,
            inProduction = dto.inProduction,
            languages = dto.languages,
            voteAverage = dto.voteAverage,
            genreIds = dto.genres.map(GenreJsonResult::id),
            status = dto.status,
            tagline = dto.tagline,
            type = dto.type,
            posterPath = dto.posterPath,
            videos = dto.videos?.results?.map(videoConverter::toDomainModel),
            cast = dto.credits?.cast?.map(cinematicCastConverter::toDomainModel)
        )
    }
}