package com.gery.apprenticegallery.domain.photos.model

data class PhotosData(
    val nextPage: String?,
    val totalResults: Int,
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>
)