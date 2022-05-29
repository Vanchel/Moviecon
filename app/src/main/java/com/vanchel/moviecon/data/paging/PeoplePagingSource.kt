package com.vanchel.moviecon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vanchel.moviecon.data.network.models.PersonResponse
import com.vanchel.moviecon.data.network.services.PeopleService
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.PersonType

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource] для постраничного получения людей.
 *
 * @property service Источник данных о людях
 * @constructor Создает [PagingSource] для получения информации о людях
 *
 * @param type Категория людей для загрузки
 */
class PeoplePagingSource(
    private val service: PeopleService,
    type: PersonType
) : PagingSource<Int, Person>() {
    private val endpoint = mapTypeToEndpoint(type)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val result = endpoint(page)
            LoadResult.Page(
                data = result.results?.map(PersonResponse::transform) ?: listOf(),
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

    private fun mapTypeToEndpoint(type: PersonType) = when (type) {
        PersonType.POPULAR -> service::getPopular
    }
}