package com.vanchel.moviecon.data.network.models

/**
 * @author Иван Тимашов
 *
 * Класс данных, содержащий полученные с сервиса TMDB данные об изображениях, приложенных к фильму
 * или сериалу.
 *
 * @property id идентификатор фильма или сериала
 * @property backdrops список изображений, выступающих в роли обложек
 * @property posters список изображений, выступающих в роли постеров
 */
data class ImagesJsonResult(
    val id: Int,
    val backdrops: List<ImageJsonResult>,
    val posters: List<ImageJsonResult>
)