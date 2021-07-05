package com.gery.apprenticegallery.di


import com.gery.apprenticegallery.data.network.photos.PhotosApiService
import com.gery.apprenticegallery.data.network.photos.PhotosApiServiceImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface NetworkModule {

    @Singleton
    @Binds
    fun bindsImagesApiService(impl: PhotosApiServiceImplementation): PhotosApiService
}
