package com.vanchel.moviecon.data.network.services

import com.vanchel.moviecon.data.network.models.PageResponse
import com.vanchel.moviecon.data.network.models.PersonDetailsResponse
import com.vanchel.moviecon.data.network.models.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Иван Тимашов
 *
 * Интерфейс взаимодействия с сервисом tmdb.org для получения информации о людях.
 */
interface PeopleService {
    /**
     * Запрашивает подробную информацию о человеке.
     *
     * @param personId Идентификатор человека
     * @return Подробная информация о запрашиваемом человеке
     */
    @GET("person/{person_id}?append_to_response=movie_credits,tv_credits,external_ids")
    suspend fun getDetails(@Path("person_id") personId: Int): PersonDetailsResponse

    /**
     * Запрашивает список популярных людей.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о популярных людях
     */
    @GET("person/popular")
    suspend fun getPopular(@Query("page") page: Int = 1): PageResponse<PersonResponse>
}