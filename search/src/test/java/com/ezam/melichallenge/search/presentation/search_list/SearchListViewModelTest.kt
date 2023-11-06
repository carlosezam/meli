package com.ezam.melichallenge.search.presentation.search_list

import androidx.lifecycle.SavedStateHandle
import arrow.core.left
import arrow.core.right
import com.ezam.melichallenge.search.MainDispatcherRule
import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.data.repository.SearchRepositoryImpl
import com.ezam.melichallenge.search.domain.model.Product
import com.ezam.melichallenge.search.domain.repository.model.SearchProductError
import com.ezam.melichallenge.search.domain.repository.model.SearchProductResult
import com.ezam.yaperecipies.presentation.model.Text
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SearchListViewModelTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var searchRepositoryImpl: SearchRepositoryImpl

    private val products = listOf(
        Product("id1", "name1", "url1", "price1"),
        Product("id2", "name2", "url2", "price2"),
        Product("id3", "name3", "url3", "price3"),
    )

    @Test
    fun `muestra la lista productos exitosamente`() {
        // given
        val params = mapOf(SEARCH_ARG to "lorem")
        val viewModel = SearchListViewModel(SavedStateHandle(params), searchRepositoryImpl)

        val searchResult = SearchProductResult<SearchPaginationImpl>(
            results = products,
            next = SearchPaginationImpl( offset = 0, limit = 3, total = 3)
        )
        coEvery { searchRepositoryImpl.searchProduct(any(), any()) } returns searchResult.right()

        // when
        viewModel.loadNextPage()

        //then
        val state = viewModel.getState().value
        Truth.assertThat(state.error).isNull()
        Truth.assertThat( state.results?.size).isEqualTo(products.size)

    }

    @Test
    fun `concatena los resultados cuando se invoca nuevamente loadNextPage`() {
        // given
        val params = mapOf(SEARCH_ARG to "lorem")
        val viewModel = SearchListViewModel(SavedStateHandle(params), searchRepositoryImpl)

        val searchResult = SearchProductResult<SearchPaginationImpl>(
            results = products,
            next = SearchPaginationImpl( offset = 0, limit = 3, total = 6)
        )
        coEvery { searchRepositoryImpl.searchProduct(any(), any()) } returns searchResult.right()

        // when
        viewModel.loadNextPage()
        viewModel.loadNextPage()

        //then
        val state = viewModel.getState().value
        Truth.assertThat(state.error).isNull()
        Truth.assertThat( state.results?.size).isEqualTo(products.size * 2)

    }

    @Test
    fun `canLoadMoreData = false cuando se obtiene una lista vacia`() {
        // given
        val params = mapOf(SEARCH_ARG to "lorem")
        val viewModel = SearchListViewModel(SavedStateHandle(params), searchRepositoryImpl)

        val searchResult = SearchProductResult<SearchPaginationImpl>(
            results = products,
            next = SearchPaginationImpl( offset = 0, limit = 3, total = 3)
        )
        val searchResultEmpty = SearchProductResult<SearchPaginationImpl>(
            results = emptyList(),
            next = SearchPaginationImpl( offset = 3, limit = 3, total = 3)
        )
        coEvery { searchRepositoryImpl.searchProduct(any(), any()) } returns searchResult.right() andThen searchResultEmpty.right()

        // when
        viewModel.loadNextPage()
        viewModel.loadNextPage()

        //then
        val state = viewModel.getState().value

        Truth.assertThat( state.results?.size).isEqualTo(products.size)
        Truth.assertThat(state.canLoadMoreData).isEqualTo(false)
    }

    @Test
    fun `no realiza mas llamadas el api cuando alcanza el fin de los datos`() {
        // given
        val params = mapOf(SEARCH_ARG to "lorem")
        val viewModel = SearchListViewModel(SavedStateHandle(params), searchRepositoryImpl)

        val searchResult = SearchProductResult<SearchPaginationImpl>(
            results = products.subList(0,2),
            next = SearchPaginationImpl( offset = 0, limit = 2, total = 3)
        )
        val searchResultEmpty = SearchProductResult<SearchPaginationImpl>(
            results = products.subList(2,3),
            next = SearchPaginationImpl( offset = 2, limit = 2, total = 3)
        )
        coEvery { searchRepositoryImpl.searchProduct(any(), any()) } returns searchResult.right() andThen searchResultEmpty.right()

        // when
        viewModel.loadNextPage() // descarga datos
        viewModel.loadNextPage() // alcanza final de los datos

        viewModel.loadNextPage() // ya no deberia intentar descargar mas
        viewModel.loadNextPage() // ya no deberia intentar descargar mas

        //then

        coVerify(exactly = 2) { searchRepositoryImpl.searchProduct(any(), any()) }
    }

    @Test
    fun `no realiza mas llamadas el api cuando alcanza el fin de los datos 2`() {
        // given
        val params = mapOf(SEARCH_ARG to "lorem")
        val viewModel = SearchListViewModel(SavedStateHandle(params), searchRepositoryImpl)

        val searchResult = SearchProductResult<SearchPaginationImpl>(
            results = products,
            next = SearchPaginationImpl( offset = 0, limit = 3, total = products.size)
        )
        val searchResultEmpty = SearchProductResult<SearchPaginationImpl>(
            results = emptyList(),
            next = SearchPaginationImpl( offset = 3, limit = 3, total = products.size)
        )
        coEvery { searchRepositoryImpl.searchProduct(any(), any()) } returns searchResult.right() andThen searchResultEmpty.right()

        // when
        viewModel.loadNextPage() // descarga datos
        viewModel.loadNextPage() // alcanza final de los datos

        viewModel.loadNextPage() // ya no deberia intentar descargar mas
        viewModel.loadNextPage() // ya no deberia intentar descargar mas

        //then

        coVerify(exactly = 1) { searchRepositoryImpl.searchProduct(any(), any()) }
    }

    @Test
    fun `muestra error user-friendly cuando hay problemas de conexion`(){
        // given
        val params = mapOf(SEARCH_ARG to "lorem")
        val viewModel = SearchListViewModel(SavedStateHandle(params), searchRepositoryImpl)

        coEvery { searchRepositoryImpl.searchProduct(any(),any()) } returns SearchProductError.InternetError.left()

        //when
        viewModel.loadNextPage()

        //
        val state = viewModel.getState().value

        Truth.assertThat(state.error).isEqualTo(Text.StringRes(R.string.msg_revisa_tu_conexion_a_internet))
    }

    @Test
    fun `muestra error user-friendly cuando hay problemas inesperados`(){
        // given
        val params = mapOf(SEARCH_ARG to "lorem")
        val viewModel = SearchListViewModel(SavedStateHandle(params), searchRepositoryImpl)

        coEvery { searchRepositoryImpl.searchProduct(any(),any()) } returns SearchProductError.UnknownError.left()

        //when
        viewModel.loadNextPage()

        //then
        val state = viewModel.getState().value

        Truth.assertThat(state.error).isEqualTo(Text.StringRes(R.string.msg_ha_ocurrido_un_error_inesperado))
    }
}