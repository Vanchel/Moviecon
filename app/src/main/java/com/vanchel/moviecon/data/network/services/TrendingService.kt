package com.vanchel.moviecon.data.network.services

import com.vanchel.moviecon.data.network.models.MovieJsonResult
import com.vanchel.moviecon.data.network.models.PageJsonResult
import com.vanchel.moviecon.data.network.models.PersonJsonResult
import com.vanchel.moviecon.data.network.models.TvJsonResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MEDIA_TYPE_MOVIE = "movie"
private const val MEDIA_TYPE_TV = "tv"
private const val MEDIA_TYPE_PERSON = "person"

/**
 * @author Иван Тимашов
 *
 * Интерфейс взаимодействия с сервисом tmdb.org для получения информации о трендах.
 */
interface TrendingService {
    /**
     * Запрашивает список фильмов, которые сегодня в тренде.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о фильмах в тренде
     */
    @GET("trending/$MEDIA_TYPE_MOVIE/day")
    fun trendingMovie(@Query("page") page: Int = 1): Single<PageJsonResult<MovieJsonResult>>

    /**
     * Запрашивает список сериалов, которые сегодня в тренде.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о сериалах в тренде
     */
    @GET("trending/$MEDIA_TYPE_TV/day")
    fun trendingTv(@Query("page") page: Int = 1): Single<PageJsonResult<TvJsonResult>>

    /**
     * Запрашивает список людей, которые сегодня в тренде.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о людях в тренде
     */
    @GET("trending/$MEDIA_TYPE_PERSON/day")
    fun trendingPerson(@Query("page") page: Int = 1): Single<PageJsonResult<PersonJsonResult>>
}