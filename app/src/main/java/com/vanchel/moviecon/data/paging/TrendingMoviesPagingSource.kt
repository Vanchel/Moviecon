package com.vanchel.moviecon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vanchel.moviecon.data.network.models.MovieResponse
import com.vanchel.moviecon.data.network.services.TrendingService
import com.vanchel.moviecon.domain.entities.Movie

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource] для постраничного получения фильмов в тренде.
 *
 * @property service Источник данных о фильмах в тренде
 */
class TrendingMoviesPagingSource(
    private val service: TrendingService
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val result = service.trendingMovie(page)
            LoadResult.Page(
                data = result.results?.map(MovieResponse::transform) ?: listOf(),
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == result.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            // TODO обрабатывать исключения явным образом
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}