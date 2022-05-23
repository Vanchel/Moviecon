package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Возможные статусы картины
 */
enum class CinematicStatus(val value: String) {
    RUMORED("Rumored"),
    PLANNED("Planned"),
    IN_PRODUCTION("In Production"),
    POST_PRODUCTION("Post Production"),
    RELEASED("Released"),
    CANCELED("Canceled");

    companion object {
        fun byValue(value: String?) = values().firstOrNull { it.value == value } ?: CANCELED
    }
}