package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.MovieJsonResult
import com.vanchel.moviecon.data.network.models.PersonJsonResult
import com.vanchel.moviecon.data.network.models.TvJsonResult
import com.vanchel.moviecon.data.network.services.TrendingService
import com.vanchel.moviecon.data.paging.TrendingMoviesPagingSource
import com.vanchel.moviecon.data.paging.TrendingPeoplePagingSource
import com.vanchel.moviecon.data.paging.TrendingTvsPagingSource
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.domain.repositories.TrendingRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [TrendingRepository].
 *
 * @property trendingService Источник данных о трендах
 * @property movieConverter Конвертер информации о фильмах
 * @property tvConverter Конвертер информации о сериалах
 * @property personConverter Конвертер информации о людях
 * @property schedulers Планировщики для выполнения асинхронных задач
 */
class TrendingRepositoryImpl @Inject constructor(
    private val trendingService: TrendingService,
    private val movieConverter: EntityConverter<Movie, MovieJsonResult>,
    private val tvConverter: EntityConverter<Tv, TvJsonResult>,
    private val personConverter: EntityConverter<Person, PersonJsonResult>,
    private val schedulers: Schedulers
) : TrendingRepository {
    override fun getTrendingMoviesStream(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TrendingMoviesPagingSource(trendingService, movieConverter, schedulers)
            }
        ).flowable
    }

    override fun getTrendingTvsStream(): Flowable<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TrendingTvsPagingSource(trendingService, tvConverter, schedulers)
            }
        ).flowable
    }

    override fun getTrendingPeopleStream(): Flowable<PagingData<Person>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TrendingPeoplePagingSource(trendingService, personConverter, schedulers)
            }
        ).flowable
    }
}