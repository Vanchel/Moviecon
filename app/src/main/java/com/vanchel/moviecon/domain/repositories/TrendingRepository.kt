package com.vanchel.moviecon.domain.repositories

import androidx.paging.PagingData
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.domain.entities.Tv
import kotlinx.coroutines.flow.Flow

/**
 * @author Иван Тимашов
 *
 * Интерфейс получения данных о трендах.
 */
interface TrendingRepository {
    /**
     * Получает поток трендовых фильмов.
     *
     * @return Поток трендовых фильмов
     */
    fun getTrendingMoviesStream(): Flow<PagingData<Movie>>

    /**
     * Получает поток трендовых сериалов.
     *
     * @return Поток трендовых сериалов
     */
    fun getTrendingTvsStream(): Flow<PagingData<Tv>>

    /**
     * Получает поток трендовых людей.
     *
     * @return Поток трендовых людей
     */
    fun getTrendingPeopleStream(): Flow<PagingData<Person>>
}