package com.ezam.melichallenge.search.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDTO(
    @SerialName("paging")
    val paging: PagingDTO,
    @SerialName("results")
    val results: List<ResultDTO>
){

    @Serializable
    data class PagingDTO(
        @SerialName("total")
        val total: Int,
        @SerialName("offset")
        val offset: Int,
        @SerialName("limit")
        val limit: Int
    )

    @Serializable
    data class ResultDTO(
        @SerialName("id")
        val id: String,
        @SerialName("title")
        val title: String,
        @SerialName("thumbnail")
        val thumbnail: String,
        @SerialName("price")
        val price: Float
    )
}
