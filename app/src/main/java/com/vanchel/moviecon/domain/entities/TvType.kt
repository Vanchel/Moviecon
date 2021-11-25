package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Перечисление, содержащее возможные категории сериалов.
 */
enum class TvType {
    /**
     * популярные сериалы
     */
    POPULAR,

    /**
     * сериалы, которые сегодня в эфире
     */
    AIRING_TODAY,

    /**
     * сериалы, которые идут по телевидению
     */
    ON_THE_AIR,

    /**
     * лучшие сериалы
     */
    TOP_RATED
}
