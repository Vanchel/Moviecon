package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanchel.moviecon.data.network.services.MoviesService
import com.vanchel.moviecon.data.paging.MoviesPagingSource
import com.vanchel.moviecon.domain.entities.*
import com.vanchel.moviecon.domain.repositories.MoviesRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [MoviesRepository].
 *
 * @property moviesService Источник данных о фильмах
 */
class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService
) : MoviesRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return moviesService.getDetails(movieId).transform()
    }

    override suspend fun getMovieCredits(movieId: Int): List<Cast> {
        return moviesService.getCredits(movieId).transform()
    }

    override suspend fun getMovieImages(movieId: Int): Images {
        return moviesService.getImages(movieId).transform()
    }

    override fun getMoviesStream(type: MovieType): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                MoviesPagingSource(moviesService, type)
            }
        ).flow
    }
}