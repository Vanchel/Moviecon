package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCreditsJsonResult
import com.vanchel.moviecon.data.network.models.ImagesJsonResult
import com.vanchel.moviecon.data.network.models.MovieDetailsJsonResult
import com.vanchel.moviecon.data.network.models.MovieJsonResult
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
 * @property movieConverter Конвертер информации о фильмах
 * @property movieDetailsConverter Конвертер подробной информации о фильмах
 * @property castConverter Конвертер информации об актерах
 * @property imagesConverter Конвертер информации об изображениях
 * @property schedulers Планировщики для выполнения асинхронных задач
 */
class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val movieConverter: EntityConverter<Movie, MovieJsonResult>,
    private val movieDetailsConverter: EntityConverter<MovieDetails, MovieDetailsJsonResult>,
    private val castConverter: EntityConverter<List<Cast>, CinematicCreditsJsonResult>,
    private val imagesConverter: EntityConverter<Images, ImagesJsonResult>,
    private val schedulers: Schedulers
) : MoviesRepository {
    override fun getMovieDetails(movieId: Int): Single<MovieDetails> {
        return moviesService.getDetails(movieId).map(movieDetailsConverter::toDomainModel)
    }

    override fun getMovieCredits(movieId: Int): Single<List<Cast>> {
        return moviesService.getCredits(movieId).map(castConverter::toDomainModel)
    }

    override fun getMovieImages(movieId: Int): Single<Images> {
        return moviesService.getImages(movieId).map(imagesConverter::toDomainModel)
    }

    override fun getMoviesStream(type: MovieType): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                MoviesPagingSource(moviesService, movieConverter, schedulers, type)
            }
        ).flowable
    }
}