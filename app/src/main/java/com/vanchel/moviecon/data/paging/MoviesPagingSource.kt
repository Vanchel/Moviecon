package com.vanchel.moviecon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vanchel.moviecon.data.network.models.MovieResponse
import com.vanchel.moviecon.data.network.services.MoviesService
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.MovieType

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource] для постраничного получения фильмов.
 *
 * @property service Источник данных о фильмах
 * @constructor Создает [PagingSource] для получения информации о фильмах
 *
 * @param type Категория фильмов для загрузки
 */
class MoviesPagingSource(
    private val service: MoviesService,
    type: MovieType
) : PagingSource<Int, Movie>() {
    private val endpoint = mapTypeToEndpoint(type)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val result = endpoint(page)
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

    private fun mapTypeToEndpoint(type: MovieType) = when (type) {
        MovieType.POPULAR -> service::getPopular
        MovieType.NOW_PLAYING -> service::getNowPlaying
        MovieType.UPCOMING -> service::getUpcoming
        MovieType.TOP_RATED -> service::getTopRated
    }
}