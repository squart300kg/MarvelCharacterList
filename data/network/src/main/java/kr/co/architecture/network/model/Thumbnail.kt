package kr.co.architecture.network.model

import com.google.gson.annotations.SerializedName
import kr.co.architecture.network.constant.ImageScaleType

data class Thumbnail(
    @SerializedName("extension")
    val extension: String,
    @SerializedName("path")
    val path: String
) {
    val imageFullPath: String
        get() = "${path.replace("http", "https")}/${ImageScaleType.LANDSCAPE_XLARGE.value}.$extension"
}