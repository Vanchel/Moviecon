package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.MovieJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Movie
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [MovieJsonResult] в тип [Movie].
 */
class MovieToMovieJsonResultConverter @Inject constructor() :
    EntityConverter<Movie, MovieJsonResult> {
    override fun toDomainModel(dto: MovieJsonResult): Movie {
        return Movie(
            id = dto.id,
            isAdult = dto.adult,
            backdropPath = dto.backdropPath,
            genreIds = dto.genreIds,
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            title = dto.title,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            popularity = dto.popularity
        )
    }
}