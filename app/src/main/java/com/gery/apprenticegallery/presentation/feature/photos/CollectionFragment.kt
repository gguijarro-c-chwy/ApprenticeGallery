package com.gery.apprenticegallery.presentation.feature.photos

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.gery.apprenticegallery.MainActivity
import com.gery.apprenticegallery.R
import com.gery.apprenticegallery.base_mvi.MviFragment
import com.gery.apprenticegallery.base_mvi.View
import com.gery.apprenticegallery.databinding.FragmentCollectionBinding
import com.gery.apprenticegallery.domain.photos.model.Photo
import com.gery.apprenticegallery.presentation.common.*
import com.gery.apprenticegallery.presentation.feature.photo_details.PhotoDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_collection.*


interface CollectionView : View<CollectionViewState, CollectionViewEffect, CollectionIntent>

const val columns = 3

@AndroidEntryPoint
class CollectionFragment : MviFragment<
        CollectionViewState,
        CollectionViewEffect,
        CollectionView,
        CollectionPresenter,
        FragmentCollectionBinding>(R.layout.fragment_collection), CollectionView {

    private lateinit var adapter: CollectionPagerAdapter
    private var loadingData = false
    private val intentsPubSub = PublishSubject.create<CollectionIntent>()
    private val searchViewModel: MainActivity.SearchViewModel by activityViewModels()

    override val binding: FragmentCollectionBinding by viewBinding(FragmentCollectionBinding::bind)
    override val presenter: CollectionPresenter by viewModels()
    override fun getMviView(): CollectionView = this

    override fun handleViewEffect(event: CollectionViewEffect) {
        when (event) {
            is CollectionViewEffect.NavigateToPhotoDetails -> navigateToPhotoDetails(event.photo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CollectionPagerAdapter({
            intentsPubSub.onNext(CollectionIntent.NavigateToPhotoDetails(it))
        })

        val searchQueryObserver = Observer<String> {
            intentsPubSub.onNext(CollectionIntent.OnSearchQueryChange(it))
        }

        searchViewModel.searchQuery.observe(this, searchQueryObserver)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpGrid()
    }

    private fun setUpGrid() {
        val manager = GridLayoutManager(requireContext(), columns)
        val infiniteScrollListener =
            object : InfiniteScrollListener(manager) {
                override fun onLoadMore() {
                    if (!loadingData) {
                        loadingData = true
                        intentsPubSub.onNext(CollectionIntent.LoadMoreData)
                    }
                }

                override fun isDataLoading(): Boolean {
                    return loadingData
                }
            }

        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(infiniteScrollListener)
        recyclerView.setHasFixedSize(true)
    }

    override fun render(viewState: CollectionViewState) {
        noDataGroup.toVisibleOrGone(viewState.photos.isEmpty())
        adapter.submitList(viewState.photos)

        loadingData = viewState.showProgressBar
        loadingIndicator.toVisibleOrGone(loadingData)
    }

    override fun emitIntents(): Observable<CollectionIntent> = Observable.merge(listOf(intentsPubSub))

    private fun navigateToPhotoDetails(photo: Photo) {
        this.hideKeyboard()

        findNavController(this).navigate(
            R.id.action_CollectionFragment_to_photoDetailsFragment,
            Bundle().apply {
                putString(PhotoDetailsFragment.ARG_PHOTO_URL, photo.large2x)
                putString(PhotoDetailsFragment.ARG_PHOTO_AVERAGE_COLOR, photo.averageColor)
            }
        )
    }
}