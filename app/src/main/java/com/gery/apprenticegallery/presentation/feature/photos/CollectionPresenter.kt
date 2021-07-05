package com.gery.apprenticegallery.presentation.feature.photos

import com.gery.apprenticegallery.base_mvi.Presenter
import com.gery.apprenticegallery.di.SchedulersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CollectionPresenter @Inject constructor(
    @Named(SchedulersModule.MAIN_THREAD) mainThread: Scheduler,
    private val interactor: CollectionInteractor
) : Presenter<CollectionViewState, CollectionView, CollectionPartialState, CollectionIntent, CollectionViewEffect>(
    mainThread
) {
    override val defaultViewState: CollectionViewState
        get() = CollectionViewState()

    override fun intentToPartialState(intent: CollectionIntent): Observable<CollectionPartialState> =
        when (intent) {
            is CollectionIntent.NavigateToPhotoDetails -> Observable.just(
                CollectionPartialState.NavigateToPhotoDetails(
                    intent.photo
                )
            )
            is CollectionIntent.OnSearchQueryChange -> {
                if (intent.searchQuery != getViewState().searchQuery) {
                    interactor.searchPhotos(intent.searchQuery)
                        .startWith(Observable.just(CollectionPartialState.UpdateSearchQuery(intent.searchQuery)))
                } else {
                    Observable.never()
                }
            }
            CollectionIntent.LoadMoreData -> {
                val viewState = getViewState()
                if (viewState.pageData != null && viewState.searchQuery != null && viewState.nextPage != null) {
                    interactor.loadNextPage(viewState.searchQuery, viewState.pageData.currentPage + 1)
                } else {
                    Observable.never() //TODO: Do something about this case
                }
            }
        }
}
