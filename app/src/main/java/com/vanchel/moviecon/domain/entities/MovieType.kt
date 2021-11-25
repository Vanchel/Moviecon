package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Перечисление, содержащее возможные категории фильмов.
 */
enum class MovieType {
    /**
     * популярные фильмы
     */
    POPULAR,

    /**
     * фильмы, которые сейчас смотрят
     */
    NOW_PLAYING,

    /**
     * ожидаемые фильмы
     */
    UPCOMING,

    /**
     * лучшие фильмы
     */
    TOP_RATED
}