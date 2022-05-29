package com.vanchel.moviecon.data.network.services

import com.vanchel.moviecon.data.network.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Иван Тимашов
 *
 * Интерфейс взаимодействия с сервисом tmdb.org для получения информации о сериалах.
 */
interface TvService {
    /**
     * Запрашивает подробную информацию о сериале.
     *
     * @param tvId Идентификатор сериала
     * @return Подробная информация о запрашиваемом сериале
     */
    @GET("tv/{tv_id}?append_to_response=videos,credits")
    suspend fun getDetails(@Path("tv_id") tvId: Int): TvDetailsResponse

    /**
     * Запрашивает информацию о съемочной группе сериала.
     *
     * @param tvId Идентификатор сериала
     * @return Информация о съемочной группе сериала
     */
    @GET("tv/{tv_id}/credits")
    suspend fun getCredits(@Path("tv_id") tvId: Int): CinematicCreditsResponse

    /**
     * Запрашивает информацию об изображениях, приложенных к сериалу.
     *
     * @param tvId Идентификатор сериала
     * @return Информация об изображениях
     */
    @GET("tv/{tv_id}/images")
    suspend fun getImages(@Path("tv_id") tvId: Int): ImagesResponse

    /**
     * Запрашивает список популярных сериалов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о популярных сериалах
     */
    @GET("tv/popular")
    suspend fun getPopular(@Query("page") page: Int = 1): PageResponse<TvResponse>

    /**
     * Запрашивает список сериалов, которые сегодня в эфире.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о сериалах, которые сегодня в эфире
     */
    @GET("tv/airing_today")
    suspend fun getTvAiringToday(@Query("page") page: Int = 1): PageResponse<TvResponse>

    /**
     * Запрашивает список сериалов, идущих по телевидению.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о сериалах, идущих по телевидению
     */
    @GET("tv/on_the_air")
    suspend fun getTvOnTheAir(@Query("page") page: Int = 1): PageResponse<TvResponse>

    /**
     * Запрашивает список лучших сериалов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о лучших сериалах
     */
    @GET("tv/top_rated")
    suspend fun getTopRated(@Query("page") page: Int = 1): PageResponse<TvResponse>
}