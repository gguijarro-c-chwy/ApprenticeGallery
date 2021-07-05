package com.gery.apprenticegallery.data.network.photos

import com.gery.apprenticegallery.data.features.photos.dto.Photos
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface PhotosApiService {
    fun searchPhotos(searchQuery: String, itemsPerPage: Int = 30, page: Int): Observable<Photos>
}

class PhotosApiServiceImplementation @Inject constructor() : PhotosApiService {

    override fun searchPhotos(searchQuery: String, itemsPerPage: Int, page: Int): Observable<Photos> {
        return ServiceBuilder
            .buildService()
            .searchPhotos(searchQuery = searchQuery, page = page, perPage = itemsPerPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
