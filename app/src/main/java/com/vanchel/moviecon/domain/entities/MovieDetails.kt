package com.vanchel.moviecon.domain.entities

import java.util.*

private const val DEFAULT_TOP_CAST_LIMIT = 10

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий подробные данные о фильме.
 *
 * @property id идентификатор записи о фильме
 * @property isAdult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property backdropPath ссылка на обложку фильма
 * @property budget бюджет фильма
 * @property genreNames список названий жанров, к которым относится фильм
 * @property originalLanguage исходный язык фильма
 * @property originalTitle исходное название фильма
 * @property overview краткое описание фильма
 * @property popularity популярность фильма
 * @property posterPath ссылка на постер фильма
 * @property releaseDate дата выхода фильма
 * @property revenue кассовые сборы фильма
 * @property runTime продолжительность фильма
 * @property status статус фильма
 * @property tagline слоган фильма
 * @property title название фильма
 * @property voteAverage средняя оценка фильма
 * @property voteCount количество пользовательских оценок
 * @property videos список видеозаписей, относящихся к фильму
 * @property cast список актеров, снявшихся в фильме
 */
data class MovieDetails(
    val id: Int,
    val isAdult: Boolean,
    val backdropPath: String?,
    val budget: Int,
    val genreNames: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: Date?,
    val revenue: Long,
    val runTime: Int?,
    val status: CinematicStatus,
    val tagline: String?,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val videos: List<Video>?,
    val cast: List<Cast>?
) {
    /**
     * Официальный трейлер к фильму, если таковой есть
     */
    val officialTrailer: Video?
        get() = videos?.find(Video::isOfficial)

    /**
     * Метод получения списка актеров, отсортированный по их популярности
     *
     * @param limit максимальное количество результатов
     * @return список популярных актеров
     */
    fun getTopCast(limit: Int = DEFAULT_TOP_CAST_LIMIT): List<Cast>? {
        return cast?.sortedByDescending(Cast::popularity)?.take(limit)
    }
}