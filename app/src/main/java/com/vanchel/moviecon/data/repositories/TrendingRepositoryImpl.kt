package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanchel.moviecon.data.network.services.TrendingService
import com.vanchel.moviecon.data.paging.TrendingMoviesPagingSource
import com.vanchel.moviecon.data.paging.TrendingPeoplePagingSource
import com.vanchel.moviecon.data.paging.TrendingTvsPagingSource
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.domain.repositories.TrendingRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [TrendingRepository].
 *
 * @property trendingService Источник данных о трендах
 */
class TrendingRepositoryImpl @Inject constructor(
    private val trendingService: TrendingService
) : TrendingRepository {
    override fun getTrendingMoviesStream(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TrendingMoviesPagingSource(trendingService)
            }
        ).flow
    }

    override fun getTrendingTvsStream(): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TrendingTvsPagingSource(trendingService)
            }
        ).flow
    }

    override fun getTrendingPeopleStream(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TrendingPeoplePagingSource(trendingService)
            }
        ).flow
    }
}