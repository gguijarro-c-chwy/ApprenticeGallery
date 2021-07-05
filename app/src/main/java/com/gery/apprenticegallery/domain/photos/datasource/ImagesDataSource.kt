package com.gery.apprenticegallery.domain.photos.datasource

import com.gery.apprenticegallery.domain.photos.model.PhotosData
import io.reactivex.rxjava3.core.Observable

interface PhotosDataSource {
    fun searchPhotos(searchQuery: String, itemsPerPage: Int = 30, page: Int): Observable<PhotosData>
}
