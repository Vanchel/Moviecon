package com.vanchel.moviecon.domain.entities

import java.util.*

private const val DEFAULT_TOP_CREDITS_LIMIT = 10

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий подробные данные о человеке.
 *
 * @property id идентификатор записи о человеке
 * @property isAdult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property alsoKnownAs список альтернативных имен человека
 * @property biography  краткая биография человека
 * @property birthday день рождения человека
 * @property deathDay день смерти человека
 * @property gender пол человека
 * @property homepage ссылка на собственную страницу человека
 * @property name имя человека
 * @property placeOfBirth место рождения человека
 * @property popularity популярность человека
 * @property profilePath ссылка на аватар
 * @property credits записи об участии человека в съемках картин
 * @property instagramId идентификатор человека в instagram
 */
data class PersonDetails(
    val id: Int,
    val isAdult: Boolean,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: Date?,
    val deathDay: Date?,
    val gender: Int,
    val homepage: String?,
    val name: String,
    val placeOfBirth: String?,
    val popularity: Double,
    val profilePath: String?,
    val credits: List<Credit>,
    val instagramId: String?
) {
    /**
     * Метод получения списка картин, в которых снимался актер, отсортированный по их популярности
     *
     * @param limit максимальное количество результатов
     * @return список популярных картин
     */
    fun getTopCredits(limit: Int = DEFAULT_TOP_CREDITS_LIMIT): List<Credit> {
        return credits.sortedByDescending(Credit::popularity).take(limit)
    }
}