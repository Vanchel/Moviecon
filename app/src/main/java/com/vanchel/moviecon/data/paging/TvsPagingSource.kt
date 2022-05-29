package com.vanchel.moviecon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vanchel.moviecon.data.network.models.TvResponse
import com.vanchel.moviecon.data.network.services.TvService
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.domain.entities.TvType

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource] для постраничного получения сериалов.
 *
 * @property service Источник данных о сериалах
 * @constructor Создает [PagingSource] для получения информации о сериалах
 *
 * @param type Категория сериалов для загрузки
 */
class TvsPagingSource(
    private val service: TvService,
    type: TvType
) : PagingSource<Int, Tv>() {
    private val endpoint = mapTypeToEndpoint(type)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val result = endpoint(page)
            LoadResult.Page(
                data = result.results?.map(TvResponse::transform) ?: emptyList(),
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

    private fun mapTypeToEndpoint(type: TvType) = when (type) {
        TvType.POPULAR -> service::getPopular
        TvType.AIRING_TODAY -> service::getTvAiringToday
        TvType.ON_THE_AIR -> service::getTvOnTheAir
        TvType.TOP_RATED -> service::getTopRated
    }
}