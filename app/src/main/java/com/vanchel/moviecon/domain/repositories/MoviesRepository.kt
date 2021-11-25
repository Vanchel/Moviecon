package com.vanchel.moviecon.domain.repositories

import androidx.paging.PagingData
import com.vanchel.moviecon.domain.entities.*
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * @author Иван Тимашов
 *
 * Интерфейс получения данных о фильмах.
 */
interface MoviesRepository {
    /**
     * Получает подробную информацию о фильме.
     *
     * @param movieId Идентификатор фильма
     * @return Подробная инорфмация о фильме
     */
    fun getMovieDetails(movieId: Int): Single<MovieDetails>

    /**
     * Получает информацию о съемочной группе фильма.
     *
     * @param movieId Идентификатор фильма
     * @return Информация о съемочной группе фильма
     */
    fun getMovieCredits(movieId: Int): Single<List<Cast>>

    /**
     * Получает информацию об изображениях, приложенных к фильму.
     *
     * @param movieId Идентификатор фильма
     * @return Информация об изображениях
     */
    fun getMovieImages(movieId: Int): Single<Images>

    /**
     * Получает поток фильмов, входящих в категорию [type].
     *
     * @param type Тип запрашиваемых фильмов
     * @return Поток фильмов из указанной категории
     */
    fun getMoviesStream(type: MovieType): Flowable<PagingData<Movie>>
}