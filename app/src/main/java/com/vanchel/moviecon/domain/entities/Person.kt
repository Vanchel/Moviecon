package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий краткие данные о человеке.
 *
 * @property id идентификатор записи о человеке
 * @property name имя человека
 * @property isAdult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property profilePath ссылка на аватар
 * @property popularity популярность человека
 */
data class Person(
    val id: Int,
    val name: String,
    val isAdult: Boolean,
    val profilePath: String?,
    val popularity: Double
)