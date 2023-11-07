package com.ezam.melichallenge.search.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.domain.repository.SearchRepository
import com.ezam.melichallenge.search.domain.repository.model.ProductDetailsError
import com.ezam.melichallenge.search.presentation.details.model.DetailsState
import com.ezam.melichallenge.utils.models.Image
import com.ezam.yaperecipies.presentation.model.Text
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository<SearchPaginationImpl>
) : ViewModel() {

    private val arguments = DetailsArgs(savedStateHandle)

    private val state = MutableStateFlow(DetailsState())
    fun getState() = state.asStateFlow()

    fun loadDetails() = viewModelScope.launch {

        searchRepository.getProductDetails(arguments.productId).onRight { deatils ->

            state.update {
                it.copy(
                    loading = false,
                    productName = deatils.name,
                    productImage = Image.URL(deatils.imageUrls.first()),
                    productDescription = deatils.attributes.map{ "${it.name}: ${it.value}" }
                )
            }
        }.onLeft { error ->

            val message = when( error) {
                is ProductDetailsError.NotFound -> Text.StringRes(R.string.msg_producto_no_econtrado)
                is ProductDetailsError.InternetError -> Text.StringRes(R.string.msg_revisa_tu_conexion_a_internet)
                is ProductDetailsError.UnknownError -> Text.StringRes(R.string.msg_ha_ocurrido_un_error_inesperado)
            }

            state.update {
                it.copy(loading = false, error = message)
            }
        }
    }


}