package com.ezam.melichallenge.search.data.remote.model

import com.ezam.melichallenge.search.domain.repository.model.SearchPagination

data class SearchPaginationImpl(
    val offset: Int,
    val limit: Int,
    val total: Int = 0,
) : SearchPagination