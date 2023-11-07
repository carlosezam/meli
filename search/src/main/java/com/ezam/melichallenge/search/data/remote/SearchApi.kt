package com.ezam.melichallenge.search.data.remote

import com.ezam.melichallenge.search.data.remote.model.DetailsDTO
import com.ezam.melichallenge.search.data.remote.model.SearchDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("sites/MLM/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ) : SearchDTO


    @GET("items/{id}")
    suspend fun details(
        @Path("id") id: String
    ) : DetailsDTO


    companion object {
        internal const val MELI_API_BASE_URL = "https://api.mercadolibre.com/"
    }
}