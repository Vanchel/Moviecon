package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий данные об изображении.
 *
 * @property aspectRatio соотношение сторон изображения
 * @property filePath путь к файлу
 * @property width ширина
 * @property height высота
 * @property voteAverage средняя оценка пользователей
 * @property voteCount количество пользовательских оценок
 */
data class Image(
    val aspectRatio: Double,
    val filePath: String,
    val width: Int,
    val height: Int,
    val voteAverage: Double,
    val voteCount: Int
)