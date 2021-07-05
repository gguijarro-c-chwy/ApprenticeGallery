package com.gery.apprenticegallery.presentation.feature.photos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gery.apprenticegallery.R
import com.gery.apprenticegallery.domain.photos.model.Photo
import com.gery.apprenticegallery.presentation.common.inflate
import com.gery.apprenticegallery.presentation.common.load
import kotlinx.android.synthetic.main.list_item_image.view.*

class CollectionPagerAdapter(
    private val onItemClicked: (Photo) -> Unit,
    private val images: MutableList<Photo> = mutableListOf()
) : RecyclerView.Adapter<CollectionPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_image))
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position], onItemClicked)
    }

    fun submitList(list: List<Photo>) {
        this.images.clear()
        this.images.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(image: Photo, onItemClicked: (Photo) -> Unit) {
            view.imageView.setOnClickListener {
                onItemClicked(image)
            }

            view.imageView.load(image.tiny)
        }
    }
}
