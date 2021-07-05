package com.gery.apprenticegallery.presentation.feature.photos

import com.gery.apprenticegallery.base_mvi.PartialState
import com.gery.apprenticegallery.domain.photos.model.Photo
import com.gery.apprenticegallery.domain.photos.model.PhotosData

sealed class CollectionPartialState : PartialState<CollectionViewState, CollectionViewEffect> {
    object PhotosLoading : CollectionPartialState() {
        override fun reduce(previousState: CollectionViewState): CollectionViewState {
            return previousState.copy(showProgressBar = true, error = null)
        }
    }

    data class PhotosLoaded(
        private val photosData: PhotosData
    ) : CollectionPartialState() {
        override fun reduce(previousState: CollectionViewState): CollectionViewState {
            return previousState.copy(
                photos = photosData.photos,
                showProgressBar = false,
                nextPage = photosData.nextPage,
                pageData = PageData(currentPage = photosData.page, totalResults = photosData.totalResults)
            )
        }
    }

    data class NextPageLoaded(
        private val photosData: PhotosData
    ) : CollectionPartialState() {
        override fun reduce(previousState: CollectionViewState): CollectionViewState {
            val newList = photosData.photos.toMutableList()
            newList.addAll(0, previousState.photos)
            return previousState.copy(
                photos = newList,
                showProgressBar = false,
                nextPage = photosData.nextPage,
                pageData = PageData(currentPage = photosData.page, totalResults = photosData.totalResults)
            )
        }
    }

    data class Error(
        val value: Throwable
    ) : CollectionPartialState() {
        override fun reduce(previousState: CollectionViewState): CollectionViewState {
            return previousState.copy(
                error = value,
                showProgressBar = false
            )
        }
    }

    data class NavigateToPhotoDetails(
        private val photo: Photo
    ) : CollectionPartialState() {
        override fun mapToViewEffect(): CollectionViewEffect {
            return CollectionViewEffect.NavigateToPhotoDetails(photo)
        }
    }

    data class UpdateSearchQuery(
        private val searchQuery: String
    ) : CollectionPartialState() {
        override fun reduce(previousState: CollectionViewState): CollectionViewState {
            return previousState.copy(
                searchQuery = searchQuery,
            )
        }
    }
}
