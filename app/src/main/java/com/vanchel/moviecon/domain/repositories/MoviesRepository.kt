package com.vanchel.moviecon.domain.repositories

import androidx.paging.PagingData
import com.vanchel.moviecon.domain.entities.*
import kotlinx.coroutines.flow.Flow

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
    suspend fun getMovieDetails(movieId: Int): MovieDetails

    /**
     * Получает информацию о съемочной группе фильма.
     *
     * @param movieId Идентификатор фильма
     * @return Информация о съемочной группе фильма
     */
    suspend fun getMovieCredits(movieId: Int): List<Cast>

    /**
     * Получает информацию об изображениях, приложенных к фильму.
     *
     * @param movieId Идентификатор фильма
     * @return Информация об изображениях
     */
    suspend fun getMovieImages(movieId: Int): Images

    /**
     * Получает поток фильмов, входящих в категорию [type].
     *
     * @param type Тип запрашиваемых фильмов
     * @return Поток фильмов из указанной категории
     */
    fun getMoviesStream(type: MovieType): Flow<PagingData<Movie>>
}