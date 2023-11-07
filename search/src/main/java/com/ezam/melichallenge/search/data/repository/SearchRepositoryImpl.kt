package com.ezam.melichallenge.search.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import com.ezam.melichallenge.search.data.remote.SearchApi
import com.ezam.melichallenge.search.data.remote.mappers.toProduct
import com.ezam.melichallenge.search.data.remote.mappers.toProductDetails
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.domain.model.ProductDetails
import com.ezam.melichallenge.search.domain.repository.SearchRepository
import com.ezam.melichallenge.search.domain.repository.model.ProductDetailsError
import com.ezam.melichallenge.search.domain.repository.model.SearchPagination
import com.ezam.melichallenge.search.domain.repository.model.SearchProductError
import com.ezam.melichallenge.search.domain.repository.model.SearchProductResult
import com.ezam.melichallenge.utils.utils.Logger
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val logger: Logger,
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
            logger.error( e.stackTraceToString() )
            raise( SearchProductError.InternetError )
        } catch ( e: Exception ){
            logger.error( e.stackTraceToString() )
            raise( SearchProductError.UnknownError )
        }
    }

    override suspend fun getProductDetails(productId: String): Either<ProductDetailsError, ProductDetails> = either {

        try {
            searchApi.details( productId).toProductDetails()
        }catch ( e: HttpException){
            logger.error( e.stackTraceToString() )
            raise( ProductDetailsError.NotFound )
        }catch (e: IOException){
            logger.error( e.stackTraceToString() )
            raise(ProductDetailsError.InternetError)
        }catch (e: Exception){
            logger.error( e.stackTraceToString() )
            raise(ProductDetailsError.UnknownError)
        }
    }

    companion object {
        internal const val PAGE_SIZE = 15
    }
}