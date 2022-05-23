package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Перечисление гендеров
 */
enum class Gender(val value: Int) {
    NOT_SPECIFIED(0),
    FEMALE(1),
    MALE(2),
    NON_BINARY(3);

    companion object {
        fun byValue(value: Int?) = values().firstOrNull { it.value == value } ?: NOT_SPECIFIED
    }
}