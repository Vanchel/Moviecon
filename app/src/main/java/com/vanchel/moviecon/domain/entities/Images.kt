package com.vanchel.moviecon.domain.entities

/**
 * @author Иван Тимашов
 *
 * Класс, содержащий данные об изображениях, прикрепленных к картине.
 *
 * @property cinematicId идентификатор картины
 * @property backdrops список изображений, выступающих в роли обложек
 * @property posters список изображений, выступающих в роли постеров
 */
data class Images(
    val cinematicId: Int,
    val backdrops: List<Image>,
    val posters: List<Image>
)