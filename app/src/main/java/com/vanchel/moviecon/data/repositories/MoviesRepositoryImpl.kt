package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.vanchel.moviecon.data.network.models.CinematicCreditsResponse
import com.vanchel.moviecon.data.network.models.ImagesResponse
import com.vanchel.moviecon.data.network.models.MovieDetailsResponse
import com.vanchel.moviecon.data.network.services.MoviesService
import com.vanchel.moviecon.data.paging.MoviesPagingSource
import com.vanchel.moviecon.domain.entities.*
import com.vanchel.moviecon.domain.repositories.MoviesRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [MoviesRepository].
 *
 * @property moviesService Источник данных о фильмах
 * @property schedulers Планировщики для выполнения асинхронных задач
 */
class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val schedulers: Schedulers
) : MoviesRepository {
    override fun getMovieDetails(movieId: Int): Single<MovieDetails> {
        return moviesService.getDetails(movieId).map(MovieDetailsResponse::transform)
    }

    override fun getMovieCredits(movieId: Int): Single<List<Cast>> {
        return moviesService.getCredits(movieId).map(CinematicCreditsResponse::transform)
    }

    override fun getMovieImages(movieId: Int): Single<Images> {
        return moviesService.getImages(movieId).map(ImagesResponse::transform)
    }

    override fun getMoviesStream(type: MovieType): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                MoviesPagingSource(moviesService, schedulers, type)
            }
        ).flowable
    }
}