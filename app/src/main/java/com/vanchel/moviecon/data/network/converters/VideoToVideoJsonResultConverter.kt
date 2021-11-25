package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.VideoJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Video

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [VideoJsonResult] в тип [Video].
 */
class VideoToVideoJsonResultConverter : EntityConverter<Video, VideoJsonResult> {
    override fun toDomainModel(dto: VideoJsonResult) = Video(
        id = dto.id,
        name = dto.name,
        key = dto.key,
        isOfficial = dto.official
    )
}