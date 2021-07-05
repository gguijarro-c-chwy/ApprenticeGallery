package com.gery.apprenticegallery.di

import com.gery.apprenticegallery.data.features.photos.repository.ImagesRepository
import com.gery.apprenticegallery.domain.photos.datasource.PhotosDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindImagesDataSource(impl: ImagesRepository): PhotosDataSource
}
