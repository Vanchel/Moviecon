package com.vanchel.moviecon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vanchel.moviecon.data.network.models.TvResponse
import com.vanchel.moviecon.data.network.services.TrendingService
import com.vanchel.moviecon.domain.entities.Tv

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource] для постраничного получения сериалов в тренде.
 *
 * @property service Источник данных о сериалах в тренде
 */
class TrendingTvsPagingSource(
    private val service: TrendingService
) : PagingSource<Int, Tv>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val result = service.trendingTv(page)
            LoadResult.Page(
                data = result.results?.map(TvResponse::transform) ?: listOf(),
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == result.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            // TODO обрабатывать исключения явным образом
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Tv>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}