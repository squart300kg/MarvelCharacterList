package kr.co.korean.network.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Series(
    @Expose
    @SerializedName("available")
    val available: Int,
    @Expose
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("returned")
    val returned: Int
)