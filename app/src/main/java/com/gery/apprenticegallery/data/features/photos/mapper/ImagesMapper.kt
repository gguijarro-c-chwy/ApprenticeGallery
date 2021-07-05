package com.gery.apprenticegallery.data.features.photos.mapper

import com.gery.apprenticegallery.base_mvi.BaseMapper
import com.gery.apprenticegallery.data.features.photos.dto.Photos
import com.gery.apprenticegallery.domain.photos.model.Photo
import com.gery.apprenticegallery.domain.photos.model.PhotosData
import javax.inject.Inject

class ImagesMapper @Inject constructor() : BaseMapper<Photos, PhotosData> {
    override fun transform(input: Photos): PhotosData = input.let {
        return PhotosData(
            nextPage = it.nextPage,
            page = it.page!!,
            totalResults = it.totalResults!!,
            perPage = it.perPage!!,
            photos = it.photos.mapNotNull { photo ->
                photo.src?.let { src ->
                    Photo(
                        id = photo.id!!,
                        original = src.original!!,
                        tiny = src.tiny!!,
                        large2x = src.large2x!!,
                        averageColor = photo.avgColor!!
                    )
                }
            }
        )
    }
}
