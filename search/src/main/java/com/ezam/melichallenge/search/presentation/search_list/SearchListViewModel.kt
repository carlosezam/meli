package com.ezam.melichallenge.search.presentation.search_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.domain.model.Product
import com.ezam.melichallenge.search.domain.repository.SearchRepository
import com.ezam.melichallenge.search.domain.repository.model.SearchProductError
import com.ezam.melichallenge.search.presentation.search_list.model.ResultItem
import com.ezam.melichallenge.search.presentation.search_list.model.SearchListState
import com.ezam.melichallenge.search.presentation.search_list.model.toResultItem
import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository<SearchPaginationImpl>
) : ViewModel() {

    private val arguments = SearchListArgs(savedStateHandle)

    private val state = MutableStateFlow(SearchListState())
    fun getState() = state.asStateFlow()

    private var pagination: SearchPaginationImpl? = null

    fun loadNextPage() = viewModelScope.launch {

        if(!state.value.canLoadMoreData )
            return@launch

        state.update {
            it.copy( error = null)
        }

        searchRepository.searchProduct(arguments.search, pagination)
            .onRight { searchProductResult ->

                val resultItems =  searchProductResult.results.map(Product::toResultItem)

                state.update {

                    val newResults = if(it.results == null) resultItems else it.results + resultItems
                    it.copy(
                        results = newResults,
                        error = null,
                        canLoadMoreData = resultItems.isNotEmpty() && newResults.size < searchProductResult.next.total
                    )
                }

                pagination = searchProductResult.next
            }.onLeft {
                val mesage = when( it){
                    is SearchProductError.InternetError -> Text.StringRes(R.string.msg_revisa_tu_conexion_a_internet)
                    is SearchProductError.UnknownError -> Text.StringRes(R.string.msg_ha_ocurrido_un_error_inesperado)
                }
                state.update {
                    it.copy( error = mesage)
                }
            }
    }
}