package com.gery.apprenticegallery.presentation.feature.photos

import com.gery.apprenticegallery.domain.photos.usecase.SearchPhotosUseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

private const val FIRST_PAGE = 1

class CollectionInteractor @Inject constructor(
    private val searchPhotosUseCase: SearchPhotosUseCase
) {
    // First Page Search
    fun searchPhotos(searchQuery: String, page: Int = FIRST_PAGE): Observable<CollectionPartialState> =
        searchPhotosUseCase.buildObservable(SearchPhotosUseCase.Input(searchQuery, page))
            .map<CollectionPartialState> {
                CollectionPartialState.PhotosLoaded(it)
            }
            .onErrorResumeNext {
                Observable.just(CollectionPartialState.Error(it))
            }
            .startWith(Observable.just(CollectionPartialState.PhotosLoading))

    // Paging, Load next Page
    fun loadNextPage(searchQuery: String, page: Int): Observable<CollectionPartialState> =
        searchPhotosUseCase.buildObservable(SearchPhotosUseCase.Input(searchQuery, page))
            .map<CollectionPartialState> {
                CollectionPartialState.NextPageLoaded(it)
            }
            .onErrorResumeNext {
                Observable.just(CollectionPartialState.Error(it))
            }
            .startWith(Observable.just(CollectionPartialState.PhotosLoading))
}
