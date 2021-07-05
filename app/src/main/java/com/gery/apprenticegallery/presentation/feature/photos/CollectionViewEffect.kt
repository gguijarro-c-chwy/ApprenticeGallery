package com.gery.apprenticegallery.presentation.feature.photos

import com.gery.apprenticegallery.base_mvi.ViewEffect
import com.gery.apprenticegallery.domain.photos.model.Photo

sealed class CollectionViewEffect : ViewEffect {
    data class NavigateToPhotoDetails(val photo: Photo) : CollectionViewEffect()
}
