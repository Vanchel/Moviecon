package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.vanchel.moviecon.data.network.models.PersonDetailsResponse
import com.vanchel.moviecon.data.network.services.PeopleService
import com.vanchel.moviecon.data.paging.PeoplePagingSource
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.domain.entities.PersonType
import com.vanchel.moviecon.domain.repositories.PeopleRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [PeopleRepository].
 *
 * @property peopleService Источник данных о людях.
 * @property schedulers Планировщики для выполнения асинхронных задач
 */
class PeopleRepositoryImpl @Inject constructor(
    private val peopleService: PeopleService,
    private val schedulers: Schedulers
) : PeopleRepository {
    override fun getPersonDetails(personId: Int): Single<PersonDetails> {
        return peopleService.getDetails(personId).map(PersonDetailsResponse::transform)
    }

    override fun getPeopleStream(type: PersonType): Flowable<PagingData<Person>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                PeoplePagingSource(peopleService, schedulers, type)
            }
        ).flowable
    }
}