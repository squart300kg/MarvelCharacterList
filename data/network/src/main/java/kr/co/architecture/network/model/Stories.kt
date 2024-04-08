package kr.co.architecture.network.model

import com.google.gson.annotations.SerializedName

data class Stories(
    @SerializedName("available")
    val available: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<TypeItem>,
    @SerializedName("returned")
    val returned: Int
)