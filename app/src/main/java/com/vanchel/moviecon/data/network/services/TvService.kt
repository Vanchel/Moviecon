package com.vanchel.moviecon.data.network.services

import com.vanchel.moviecon.data.network.models.*
import io.reactivex.Single
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
    fun getDetails(@Path("tv_id") tvId: Int): Single<TvDetailsJsonResult>

    /**
     * Запрашивает информацию о съемочной группе сериала.
     *
     * @param tvId Идентификатор сериала
     * @return Информация о съемочной группе сериала
     */
    @GET("tv/{tv_id}/credits")
    fun getCredits(@Path("tv_id") tvId: Int): Single<CinematicCreditsJsonResult>

    /**
     * Запрашивает информацию об изображениях, приложенных к сериалу.
     *
     * @param tvId Идентификатор сериала
     * @return Информация об изображениях
     */
    @GET("tv/{tv_id}/images")
    fun getImages(@Path("tv_id") tvId: Int): Single<ImagesJsonResult>

    /**
     * Запрашивает список популярных сериалов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о популярных сериалах
     */
    @GET("tv/popular")
    fun getPopular(@Query("page") page: Int = 1): Single<PageJsonResult<TvJsonResult>>

    /**
     * Запрашивает список сериалов, которые сегодня в эфире.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о сериалах, которые сегодня в эфире
     */
    @GET("tv/airing_today")
    fun getTvAiringToday(@Query("page") page: Int = 1): Single<PageJsonResult<TvJsonResult>>

    /**
     * Запрашивает список сериалов, идущих по телевидению.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о сериалах, идущих по телевидению
     */
    @GET("tv/on_the_air")
    fun getTvOnTheAir(@Query("page") page: Int = 1): Single<PageJsonResult<TvJsonResult>>

    /**
     * Запрашивает список лучших сериалов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о лучших сериалах
     */
    @GET("tv/top_rated")
    fun getTopRated(@Query("page") page: Int = 1): Single<PageJsonResult<TvJsonResult>>
}