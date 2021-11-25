package com.vanchel.moviecon.domain.repositories

import androidx.paging.PagingData
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.domain.entities.PersonType
import io.reactivex.Flowable
import io.reactivex.Single

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
    fun getPersonDetails(personId: Int): Single<PersonDetails>

    /**
     * Получает поток людей, входящих в категорию [type].
     *
     * @param type Тип запрашиваемых людей
     * @return Поток людей из указанной категории
     */
    fun getPeopleStream(type: PersonType): Flowable<PagingData<Person>>
}