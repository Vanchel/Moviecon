package com.vanchel.moviecon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vanchel.moviecon.data.network.models.PersonResponse
import com.vanchel.moviecon.data.network.services.TrendingService
import com.vanchel.moviecon.domain.entities.Person

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource] для постраничного получения людей в тренде.
 *
 * @property service Источник данных о людях в тренде
 */
class TrendingPeoplePagingSource(
    private val service: TrendingService
) : PagingSource<Int, Person>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val result = service.trendingPerson(page)
            LoadResult.Page(
                data = result.results?.map(PersonResponse::transform) ?: emptyList(),
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == result.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            // TODO обрабатывать исключения явным образом
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}