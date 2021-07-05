package com.gery.apprenticegallery.data.features.photos.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photos {
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null

    @SerializedName("photos")
    @Expose
    var photos: List<Photo> = emptyList()

    @SerializedName("next_page")
    @Expose
    var nextPage: String? = null
}