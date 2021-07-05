package com.gery.apprenticegallery.data.features.photos.repository

import com.gery.apprenticegallery.data.features.photos.mapper.ImagesMapper
import com.gery.apprenticegallery.data.network.photos.PhotosApiService
import com.gery.apprenticegallery.domain.photos.datasource.PhotosDataSource
import com.gery.apprenticegallery.domain.photos.model.PhotosData
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepository @Inject constructor(
    private val apiService: PhotosApiService,
    private val mapper: ImagesMapper
) : PhotosDataSource {

    override fun searchPhotos(searchQuery: String, itemsPerPage: Int, page: Int): Observable<PhotosData> =
        apiService.searchPhotos(searchQuery = searchQuery, itemsPerPage = itemsPerPage, page = page)
            .map { mapper.transform(it) }
}
