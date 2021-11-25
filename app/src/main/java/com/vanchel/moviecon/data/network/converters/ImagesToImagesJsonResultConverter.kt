package com.vanchel.moviecon.data.network.converters

import com.vanchel.moviecon.data.EntityConverter
import com.vanchel.moviecon.data.network.models.CinematicCastJsonResult
import com.vanchel.moviecon.data.network.models.ImageJsonResult
import com.vanchel.moviecon.data.network.models.ImagesJsonResult
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.domain.entities.Image
import com.vanchel.moviecon.domain.entities.Images
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основной конвертер из типа [ImagesJsonResult] в тип [Images].
 *
 * @property imageConverter конвертер из типа [ImageJsonResult] в тип [Image]
 */
class ImagesToImagesJsonResultConverter @Inject constructor(
    private val imageConverter: EntityConverter<Image, ImageJsonResult>
) : EntityConverter<Images, ImagesJsonResult> {
    override fun toDomainModel(dto: ImagesJsonResult): Images {
        return Images(
            cinematicId = dto.id,
            backdrops = dto.backdrops.map(imageConverter::toDomainModel),
            posters = dto.posters.map(imageConverter::toDomainModel)
        )
    }
}