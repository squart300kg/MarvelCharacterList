package kr.co.architecture.network.model

data class Item(
    val name: String,
    val resourceURI: String
)

data class RoleItem(
    val name: String,
    val resourceURI: String,
    val role: String
)

data class TypeItem(
    val name: String,
    val resourceURI: String,
    val type: String
)