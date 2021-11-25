package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий данные об актере из съемочной группы фильма или сериала.
 *
 * @property id идентификатор актера
 * @property isAdult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property gender пол
 * @property knownForDepartment роль актера в процессе съемок
 * @property name имя
 * @property popularity популярность актера
 * @property profilePath ссылка на аватар
 * @property castId идентификатор записи об актере в разрезе конкретной картины
 * @property character персонаж, которого актер сыграл в указанной картине
 * @property creditId идентификатор записи о полном составе съемочной группы
 * @property order порядковый номер актера
 */
data class Cast(
    val id: Int,
    val isAdult: Boolean,
    val gender: Int?,
    val knownForDepartment: String,
    val name: String,
    val popularity: Double,
    val profilePath: String?,
    val castId: Int?,
    val character: String,
    val creditId: String,
    val order: Int
)