package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий данные о видео.
 *
 * @property id идентификатор записи о видео
 * @property name название видео
 * @property key идентификатор видео на сервисе YouTube
 * @property isOfficial флаг, показывающий, является ли видео официальным
 */
data class Video(
    val id: String,
    val name: String,
    val key: String,
    val isOfficial: Boolean
)