package com.gery.apprenticegallery.domain.photos.usecase

import com.gery.apprenticegallery.base_mvi.ObservableUseCase
import com.gery.apprenticegallery.domain.photos.datasource.PhotosDataSource
import com.gery.apprenticegallery.domain.photos.model.PhotosData
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val dataSource: PhotosDataSource
) : ObservableUseCase<SearchPhotosUseCase.Input, PhotosData> {
    override fun buildObservable(params: Input): Observable<PhotosData> =
        dataSource.searchPhotos(params.searchQuery, page = params.page)

    data class Input(val searchQuery: String, val page: Int)
}
