package com.ezam.melichallenge.search.data.repository

import com.ezam.melichallenge.search.data.remote.SearchApi
import com.ezam.melichallenge.search.data.remote.model.SearchDTO
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.domain.model.Product
import com.ezam.melichallenge.search.domain.repository.model.ProductDetailsError
import com.ezam.melichallenge.search.domain.repository.model.SearchProductError
import com.ezam.melichallenge.search.domain.repository.model.SearchProductResult
import com.ezam.melichallenge.utils.utils.Logger
import com.google.common.truth.Truth
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class SearchRepositoryImplTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK
    lateinit var searchApi: SearchApi

    @MockK(relaxed = true)
    lateinit var logger: Logger

    @Test
    fun `llama al api con valores por default cuando la paginacion es nula`() = runTest {

        // given
        coEvery { searchApi.search(query = any(), limit = any(), offset = any()) } returns mockk(relaxed = true)

        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        searchRepository.searchProduct("lorem")

        // then
        coVerify { searchApi.search(query = "lorem", offset = 0, limit = SearchRepositoryImpl.PAGE_SIZE) }
    }

    @Test
    fun `llama al api con valores de paginacion`() = runTest {

        // given
        coEvery { searchApi.search(query = any(), limit = any(), offset = any()) } returns mockk(relaxed = true)

        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val paginationImpl = SearchPaginationImpl(offset = 10, limit = 20)
        searchRepository.searchProduct("lorem", paginationImpl)

        // then
        coVerify { searchApi.search(query = "lorem", offset = 10, limit = 20) }
    }

    @Test
    fun `InternetError cuando el api lanza IOException`() = runTest {

        // given
        coEvery { searchApi.search(any(),any(),any()) } throws IOException("error")
        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val result = searchRepository.searchProduct("lorem")

        //then
        result.shouldBeLeft( SearchProductError.InternetError )
    }

    @Test
    fun `UnknownError cuando el api lanza una cualquier excepcion`() = runTest {

        // given
        coEvery { searchApi.search(any(),any(),any()) } throws RuntimeException("error")
        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val result = searchRepository.searchProduct("lorem")

        //then
        result.shouldBeLeft( SearchProductError.UnknownError )
    }

    @Test
    fun `retorna los resultados mapeados al modelo de dominio`() = runTest {

        // given
        coEvery { searchApi.search(any(),any(),any()) } returns SearchDTO(
            results = listOf(SearchDTO.ResultDTO("id", "title", "thmb", 1f)),
            paging = SearchDTO.PagingDTO(10,0, 5)
        )
        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val result = searchRepository.searchProduct("lorem")

        //then
        val searchProductResult = result.shouldBeRight()

        Truth.assertThat(searchProductResult.results).isEqualTo(listOf(
            Product("id", "title","thmb", "1.0")
        ))
    }

    @Test
    fun `retorna los datos de paginacion siguientes`() = runTest {

        // given
        coEvery { searchApi.search(any(),any(),any()) } returns SearchDTO(
            results = listOf(),
            paging = SearchDTO.PagingDTO(total = 0, offset = 10, limit = 5)
        )
        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val result = searchRepository.searchProduct("lorem", SearchPaginationImpl(offset = 10, limit = 5))

        //then
        val searchProductResult = result.shouldBeRight()
        val expected = SearchPaginationImpl(
            offset = 15, limit = 5, total = 0
        )
        Truth.assertThat(searchProductResult.next).isEqualTo(expected)
    }

    val notFoundException = HttpException(  Response.error<Nothing>(404, mockk(relaxed = true)))

    @Test
    fun `retorna NotFound cuando el api de detalles responde con 404`() = runTest {

        // given
        coEvery { searchApi.details(any()) } throws notFoundException
        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val result = searchRepository.getProductDetails( "lorem")

        // then
        result.shouldBeLeft( ProductDetailsError.NotFound )
    }

    @Test
    fun `retorna InternetError cuando el api de detalles responde con error de red`() = runTest {

        // given
        coEvery { searchApi.details(any()) } throws IOException("error")
        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val result = searchRepository.getProductDetails( "lorem")

        // then
        result.shouldBeLeft( ProductDetailsError.InternetError )
    }

    @Test
    fun `retorna Unknown cuando el api de detalles responde con error desconocido`() = runTest {

        // given
        coEvery { searchApi.details(any()) } throws RuntimeException("error")
        val searchRepository = SearchRepositoryImpl(searchApi, logger)

        // when
        val result = searchRepository.getProductDetails( "lorem")

        // then
        result.shouldBeLeft( ProductDetailsError.UnknownError )
    }
}