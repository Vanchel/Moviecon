package com.vanchel.moviecon.data.network.services

import com.vanchel.moviecon.data.network.models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Иван Тимашов
 *
 * Интерфейс взаимодействия с сервисом tmdb.org для получения информации о фильмах.
 */
interface MoviesService {
    /**
     * Запрашивает подробную информацию о фильме.
     *
     * @param movieId Идентификатор фильма
     * @return Подробная информация о запрашиваемом фильме
     */
    @GET("movie/{movie_id}?append_to_response=videos,credits")
    fun getDetails(@Path("movie_id") movieId: Int): Single<MovieDetailsResponse>

    /**
     * Запрашивает информацию о съемочной группе фильма.
     *
     * @param movieId Идентификатор фильма
     * @return Информация о съемочной группе фильма
     */
    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") movieId: Int): Single<CinematicCreditsResponse>

    /**
     * Запрашивает информацию об изображениях, приложенных к фильму.
     *
     * @param movieId Идентификатор фильма
     * @return Информация об изображениях
     */
    @GET("movie/{movie_id}/images")
    fun getImages(@Path("movie_id") movieId: Int): Single<ImagesResponse>

    /**
     * Запрашивает список популярных фильмов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о популярных фильмах
     */
    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int = 1): Single<PageResponse<MovieResponse>>

    /**
     * Запрашивает список просматриваемых сейчас фильмов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о просматриваемых сейчас фильмах
     */
    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int = 1): Single<PageResponse<MovieResponse>>

    /**
     * Запрашивает список ожидаемых фильмов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные об ожидаемых фильмах
     */
    @GET("movie/upcoming")
    fun getUpcoming(@Query("page") page: Int = 1): Single<PageResponse<MovieResponse>>

    /**
     * Запрашивает список лучших фильмов.
     *
     * @param page Индекс страницы для загрузки
     * @return Данные о лучших фильмах
     */
    @GET("movie/top_rated")
    fun getTopRated(@Query("page") page: Int = 1): Single<PageResponse<MovieResponse>>
}