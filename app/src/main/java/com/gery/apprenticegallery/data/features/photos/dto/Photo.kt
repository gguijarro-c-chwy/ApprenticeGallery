package com.gery.apprenticegallery.data.features.photos.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photo {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("photographer")
    @Expose
    var photographer: String? = null

    @SerializedName("photographer_url")
    @Expose
    var photographerUrl: String? = null

    @SerializedName("photographer_id")
    @Expose
    var photographerId: Int? = null

    @SerializedName("avg_color")
    @Expose
    var avgColor: String? = null

    @SerializedName("src")
    @Expose
    var src: Src? = null

    @SerializedName("liked")
    @Expose
    var liked: Boolean? = null
}