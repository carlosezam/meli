package com.ezam.melichallenge.search.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import com.ezam.melichallenge.search.data.remote.SearchApi
import com.ezam.melichallenge.search.data.remote.mappers.toProduct
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.domain.repository.SearchRepository
import com.ezam.melichallenge.search.domain.repository.model.SearchPagination
import com.ezam.melichallenge.search.domain.repository.model.SearchProductError
import com.ezam.melichallenge.search.domain.repository.model.SearchProductResult
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository<SearchPaginationImpl> {

    override suspend fun searchProduct(
        query: String,
        pagination: SearchPaginationImpl?
    ): Either<SearchProductError, SearchProductResult<SearchPaginationImpl>> = either {

        try {
            val currentPagination = pagination ?: SearchPaginationImpl(offset =  0, limit = PAGE_SIZE, total = 0)
            val result = searchApi.search(query = query, offset = currentPagination.offset, limit = currentPagination.limit)

            SearchProductResult(
                results = result.results.map{ it.toProduct() },
                next = SearchPaginationImpl(
                    offset = currentPagination.offset + currentPagination.limit,
                    limit = currentPagination.limit,
                    total = result.paging.total
                ),
            )
        } catch ( e: IOException) {
            raise( SearchProductError.InternetError )
        } catch ( e: Exception ){
            raise( SearchProductError.UnknownError )
        }
    }


    companion object {
        internal const val PAGE_SIZE = 15
    }
}