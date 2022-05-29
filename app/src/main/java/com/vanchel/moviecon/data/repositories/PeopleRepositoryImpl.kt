package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanchel.moviecon.data.network.services.PeopleService
import com.vanchel.moviecon.data.paging.PeoplePagingSource
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.domain.entities.PersonType
import com.vanchel.moviecon.domain.repositories.PeopleRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [PeopleRepository].
 *
 * @property peopleService Источник данных о людях.
 */
class PeopleRepositoryImpl @Inject constructor(
    private val peopleService: PeopleService
) : PeopleRepository {
    override suspend fun getPersonDetails(personId: Int): PersonDetails {
        return peopleService.getDetails(personId).transform()
    }

    override fun getPeopleStream(type: PersonType): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                PeoplePagingSource(peopleService, type)
            }
        ).flow
    }
}