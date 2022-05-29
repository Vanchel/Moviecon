package com.vanchel.moviecon.domain.repositories

import androidx.paging.PagingData
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.domain.entities.PersonType
import kotlinx.coroutines.flow.Flow

/**
 * @author Иван Тимашов
 *
 * Интерфейс получения данных о людях.
 */
interface PeopleRepository {
    /**
     * Получает подробную информацию о человеке.
     *
     * @param personId Идентификатор человека
     * @return Подробная инорфмация о человеке
     */
    suspend fun getPersonDetails(personId: Int): PersonDetails

    /**
     * Получает поток людей, входящих в категорию [type].
     *
     * @param type Тип запрашиваемых людей
     * @return Поток людей из указанной категории
     */
    fun getPeopleStream(type: PersonType): Flow<PagingData<Person>>
}