package com.gery.apprenticegallery.data.network.photos

import com.gery.apprenticegallery.data.features.photos.dto.Photos
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosEndpoints {

    @GET("v1/search")
    fun searchPhotos(
        @Query("query") searchQuery: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<Photos>
}