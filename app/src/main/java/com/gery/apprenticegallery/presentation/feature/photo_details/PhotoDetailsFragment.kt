package com.gery.apprenticegallery.presentation.feature.photo_details

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gery.apprenticegallery.R
import com.gery.apprenticegallery.presentation.common.toVisibleOrGone
import kotlinx.android.synthetic.main.fragment_photo_details.*

class PhotoDetailsFragment : Fragment() {
    private lateinit var photoUrl: String
    private lateinit var averageColor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            assert(it.containsKey(ARG_PHOTO_URL))
            assert(it.containsKey(ARG_PHOTO_AVERAGE_COLOR))
            photoUrl = it.getString(ARG_PHOTO_URL)!!
            averageColor = it.getString(ARG_PHOTO_AVERAGE_COLOR)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_photo_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setBackgroundColor(Color.parseColor(averageColor))

        loadPhoto()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadPhoto() {
        Glide.with(requireContext())
            .load(photoUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingIndicator.toVisibleOrGone(true)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingIndicator.toVisibleOrGone(false)
                    return false
                }
            })
            .thumbnail(0.05f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageDetails)
    }

    companion object {
        const val ARG_PHOTO_URL = "ARG_PHOTO_URL"
        const val ARG_PHOTO_AVERAGE_COLOR = "ARG_PHOTO_AVERAGE_COLOR"
    }
}