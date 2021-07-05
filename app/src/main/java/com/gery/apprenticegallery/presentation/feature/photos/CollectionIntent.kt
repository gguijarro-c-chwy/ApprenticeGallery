package com.gery.apprenticegallery.presentation.feature.photos

import com.gery.apprenticegallery.base_mvi.Intent
import com.gery.apprenticegallery.domain.photos.model.Photo

sealed class CollectionIntent : Intent {
    object LoadMoreData : CollectionIntent()
    data class OnSearchQueryChange(val searchQuery: String) : CollectionIntent()
    data class NavigateToPhotoDetails(val photo: Photo) : CollectionIntent()
}
