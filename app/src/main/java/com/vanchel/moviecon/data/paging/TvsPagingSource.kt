package com.vanchel.moviecon.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.TvJsonResult
import com.vanchel.moviecon.data.network.services.TvService
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.domain.entities.TvType
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Single

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource][RxPagingSource] для постраничного получения сериалов.
 *
 * @property service Источник данных о сериалах
 * @property resultConverter Конвертер полученных данных
 * @property schedulers Планировщики для выполнения асинхронных задач
 * @constructor Создает [PagingSource][RxPagingSource] для получения информации о сериалах
 *
 * @param type Категория сериалов для загрузки
 */
class TvsPagingSource(
    private val service: TvService,
    private val resultConverter: EntityConverter<Tv, TvJsonResult>,
    private val schedulers: Schedulers,
    type: TvType
) : RxPagingSource<Int, Tv>() {
    private val endpoint = mapTypeToEndpoint(type)

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Tv>> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return endpoint(page)
            .subscribeOn(schedulers.io)
            .map<LoadResult<Int, Tv>> { result ->
                LoadResult.Page(
                    data = result.results.map(resultConverter::toDomainModel),
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (page == result.totalPages) null else page + 1
                )
            }.onErrorReturn { e -> LoadResult.Error(e) }
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