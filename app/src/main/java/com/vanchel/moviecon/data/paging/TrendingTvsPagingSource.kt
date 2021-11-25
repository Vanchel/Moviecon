package com.vanchel.moviecon.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.TvJsonResult
import com.vanchel.moviecon.data.network.services.TrendingService
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Single

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource][RxPagingSource] для постраничного получения сериалов в тренде.
 *
 * @property service Источник данных о сериалах в тренде
 * @property resultConverter Конвертер полученных данных
 * @property schedulers Планировщики для выполнения асинхронных задач
 */
class TrendingTvsPagingSource(
    private val service: TrendingService,
    private val resultConverter: EntityConverter<Tv, TvJsonResult>,
    private val schedulers: Schedulers
) : RxPagingSource<Int, Tv>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Tv>> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return service.trendingTv(page)
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
}