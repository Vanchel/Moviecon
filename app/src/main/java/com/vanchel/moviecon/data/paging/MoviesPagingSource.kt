package com.vanchel.moviecon.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.MovieJsonResult
import com.vanchel.moviecon.data.network.services.MoviesService
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.MovieType
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Single

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource][RxPagingSource] для постраничного получения фильмов.
 *
 * @property service Источник данных о фильмах
 * @property resultConverter Конвертер полученных данных
 * @property schedulers Планировщики для выполнения асинхронных задач
 * @constructor Создает [PagingSource][RxPagingSource] для получения информации о фильмах
 *
 * @param type Категория фильмов для загрузки
 */
class MoviesPagingSource(
    private val service: MoviesService,
    private val resultConverter: EntityConverter<Movie, MovieJsonResult>,
    private val schedulers: Schedulers,
    type: MovieType
) : RxPagingSource<Int, Movie>() {
    private val endpoint = mapTypeToEndpoint(type)

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return endpoint(page)
            .subscribeOn(schedulers.io)
            .map<LoadResult<Int, Movie>> { result ->
                LoadResult.Page(
                    data = result.results.map(resultConverter::toDomainModel),
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (page == result.totalPages) null else page + 1
                )
            }.onErrorReturn { e -> LoadResult.Error(e) }
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