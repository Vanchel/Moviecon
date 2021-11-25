package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.MovieCreditsJsonResult
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
 * Основной конвертер из типа [MovieCreditsJsonResult] в тип [Credit].
 */
class CreditToMovieCreditsJsonResultConverter @Inject constructor() :
    EntityConverter<Credit, MovieCreditsJsonResult> {
    override fun toDomainModel(dto: MovieCreditsJsonResult): Credit {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())
        val date = if (!dto.releaseDate.isNullOrBlank()) {
            dto.releaseDate.let(dateFormat::parse)
        } else {
            null
        }

        return Credit(
            id = dto.id,
            type = CinematicType.MOVIE,
            isAdult = dto.adult,
            backdropPath = dto.backdropPath,
            genreIds = dto.genreIds,
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            title = dto.title,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            popularity = dto.popularity,
            role = dto.character ?: dto.job,
            date = date
        )
    }
}