package com.vanchel.moviecon.data.network.models

import com.squareup.moshi.Json
import com.vanchel.moviecon.data.network.models.base.Transformable
import com.vanchel.moviecon.domain.entities.Images

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
data class ImagesResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "backdrops") val backdrops: List<ImageResponse>? = null,
    @Json(name = "posters") val posters: List<ImageResponse>? = null
) : Transformable<Images> {

    override fun transform() = Images(
        cinematicId = id ?: -1,
        backdrops = backdrops?.map(ImageResponse::transform) ?: listOf(),
        posters = posters?.map(ImageResponse::transform) ?: listOf()
    )
}