package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.ImageJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Image

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [ImageJsonResult] в тип [Image].
 */
class ImageToImagesJsonResultConverter : EntityConverter<Image, ImageJsonResult> {
    override fun toDomainModel(dto: ImageJsonResult): Image {
        return Image(
            aspectRatio = dto.aspectRatio,
            filePath = dto.filePath,
            width = dto.width,
            height = dto.height,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount
        )
    }
}