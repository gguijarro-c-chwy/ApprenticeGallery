package com.gery.apprenticegallery.presentation.feature.photos

import com.gery.apprenticegallery.base_mvi.ViewState
import com.gery.apprenticegallery.domain.photos.model.Photo

data class CollectionViewState(
    val photos: List<Photo> = emptyList(),
    val nextPage: String? = null,
    val pageData: PageData? = null,
    val searchQuery: String? = null,
    val showProgressBar: Boolean = false,
    val error: Throwable? = null
) : ViewState

data class PageData(val currentPage: Int, val totalResults: Int?)