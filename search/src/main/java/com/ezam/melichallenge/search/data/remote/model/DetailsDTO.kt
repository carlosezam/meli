package com.ezam.melichallenge.search.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsDTO(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("price")
    val price: Float,
    @SerialName("condition")
    val condition: String,
    @SerialName("pictures")
    val pictures: List<PictureDTO>,
    @SerialName("attributes")
    val attributes: List<AttributeDTO>,
){
    @Serializable
    data class PictureDTO(
        @SerialName("secure_url")
        val secureUrl: String
    )

    @Serializable
    data class AttributeDTO(
        @SerialName("name")
        val name: String = "",
        @SerialName("value_name")
        val valueName: String = ""
    )
}