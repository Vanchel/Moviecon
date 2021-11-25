package com.vanchel.moviecon.domain.entities

import java.util.*

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий данные об участии человека в съемках.
 *
 * @property id идентификатор записи о роли человека
 * @property type тип картины
 * @property isAdult флаг, показывающий, предназначено ли содержимое только для зрослых
 * @property backdropPath ссылка на обложку фильма
 * @property genreIds список идентификаторов жанров, к которым относится картина
 * @property originalLanguage исходный язык картины
 * @property originalTitle исходное название картины
 * @property overview краткий обзор картины
 * @property title название картины
 * @property voteAverage средняя пользовательская оценка
 * @property voteCount количество пользовательских оценок
 * @property popularity популярность картины
 * @property role роль человека в съемках
 * @property date дата выхода картины
 */
data class Credit(
    val id: Int,
    val type: CinematicType,
    val isAdult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val role: String?,
    val date: Date?
)