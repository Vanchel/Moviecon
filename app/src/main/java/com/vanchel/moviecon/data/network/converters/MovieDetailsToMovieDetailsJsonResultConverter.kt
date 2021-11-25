package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.GenreJsonResult
import com.vanchel.moviecon.data.network.models.MovieDetailsJsonResult
import com.vanchel.moviecon.data.network.models.VideoJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.MovieDetails
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.Video
import com.vanchel.moviecon.util.DATE_FORMAT_DEFAULT
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [MovieDetailsJsonResult] в тип [MovieDetails].
 *
 * @property videoConverter конвертер из типа [VideoJsonResult] в тип [Video]
 * @property cinematicCastConverter конвертер из типа [CinematicCastJsonResult] в тип [Cast]
 */
class MovieDetailsToMovieDetailsJsonResultConverter @Inject constructor(
    private val videoConverter: EntityConverter<Video, VideoJsonResult>,
    private val cinematicCastConverter: EntityConverter<Cast, CinematicCastJsonResult>
) : EntityConverter<MovieDetails, MovieDetailsJsonResult> {
    override fun toDomainModel(dto: MovieDetailsJsonResult): MovieDetails {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())
        val releaseDate = if (!dto.releaseDate.isNullOrBlank()) {
            dto.releaseDate.let(dateFormat::parse)
        } else {
            null
        }

        return MovieDetails(
            id = dto.id,
            isAdult = dto.adult,
            backdropPath = dto.backdropPath,
            budget = dto.budget,
            genreNames = dto.genres.map(GenreJsonResult::name),
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.posterPath,
            releaseDate = releaseDate,
            revenue = dto.revenue,
            runTime = dto.runtime,
            status = dto.status,
            tagline = dto.tagline,
            title = dto.title,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            videos = dto.videos?.results?.map(videoConverter::toDomainModel),
            cast = dto.credits?.cast?.map(cinematicCastConverter::toDomainModel)
        )
    }
}