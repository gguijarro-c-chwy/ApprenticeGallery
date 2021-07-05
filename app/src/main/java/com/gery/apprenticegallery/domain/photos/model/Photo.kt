package com.gery.apprenticegallery.domain.photos.model

data class Photo(
    val id: Int,
    val tiny: String,
    val original: String,
    val large2x: String,
    val averageColor: String
)