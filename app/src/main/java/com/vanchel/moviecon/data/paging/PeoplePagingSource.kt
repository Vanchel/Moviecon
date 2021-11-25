package com.vanchel.moviecon.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.PersonJsonResult
import com.vanchel.moviecon.data.network.services.PeopleService
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.PersonType
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Single

private const val STARTING_PAGE_INDEX = 1

/**
 * @author Иван Тимашов
 *
 * [PagingSource][RxPagingSource] для постраничного получения людей.
 *
 * @property service Источник данных о людях
 * @property resultConverter Конвертер полученных данных
 * @property schedulers Планировщики для выполнения асинхронных задач
 * @constructor Создает [PagingSource][RxPagingSource] для получения информации о людях
 *
 * @param type Категория людей для загрузки
 */
class PeoplePagingSource(
    private val service: PeopleService,
    private val resultConverter: EntityConverter<Person, PersonJsonResult>,
    private val schedulers: Schedulers,
    type: PersonType
) : RxPagingSource<Int, Person>() {
    private val endpoint = mapTypeToEndpoint(type)

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Person>> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return endpoint(page)
            .subscribeOn(schedulers.io)
            .map<LoadResult<Int, Person>> { result ->
                LoadResult.Page(
                    data = result.results.map(resultConverter::toDomainModel),
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (page == result.totalPages) null else page + 1
                )
            }.onErrorReturn { e -> LoadResult.Error(e) }
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