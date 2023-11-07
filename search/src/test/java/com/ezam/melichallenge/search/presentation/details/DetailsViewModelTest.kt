package com.ezam.melichallenge.search.presentation.details

import androidx.lifecycle.SavedStateHandle
import arrow.core.right
import com.ezam.melichallenge.search.MainDispatcherRule
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.domain.model.ProductAttribute
import com.ezam.melichallenge.search.domain.model.ProductDetails
import com.ezam.melichallenge.search.domain.repository.SearchRepository
import com.ezam.melichallenge.search.presentation.details.model.DetailsState
import com.ezam.melichallenge.utils.models.Image
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var searchRepository: SearchRepository<SearchPaginationImpl>

    private val productDetails = ProductDetails(
        id = "MLM2194364328",
        name = "Huawei Honor X5 32gb - 2gb Ram Dual Desbloqueado Negro",
        imageUrls = listOf("secureUrl"),
        attributes = listOf(
            ProductAttribute("Marca", "Honor"),
            ProductAttribute("Modelo", "X5")
        )
    )

    @Test
    fun `loadDetails invoca el repositorio usando el id pasado por parametro de navegacion`() {
        // given
        val params = mapOf( PRODUCT_ID to "dumbId")
        val viewModel = DetailsViewModel( SavedStateHandle(params), searchRepository )

        coEvery { searchRepository.getProductDetails(any()) } returns productDetails.right()

        // when
        viewModel.loadDetails()

        //then
        coVerify { searchRepository.getProductDetails("dumbId") }
    }

    @Test
    fun `actualiza el estado de la vista correctamente cuando invoca loadDetails()`() {
        // given
        val params = mapOf( PRODUCT_ID to "dumbId")
        val viewModel = DetailsViewModel( SavedStateHandle(params), searchRepository )

        coEvery { searchRepository.getProductDetails(any()) } returns productDetails.right()

        // when
        viewModel.loadDetails()
        val state = viewModel.getState().value

        //then
        val expected = DetailsState(
            loading = false,
            error = null,
            productName = "Huawei Honor X5 32gb - 2gb Ram Dual Desbloqueado Negro",
            productImage = Image.URL("secureUrl"),
            productDescription = listOf(
                "Marca: Honor",
                "Modelo: X5"
            )
        )
        Truth.assertThat( state).isEqualTo(expected)
    }
}