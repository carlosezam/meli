package com.ezam.melichallenge.search.presentation.search_form

import androidx.lifecycle.ViewModel
import com.ezam.melichallenge.search.presentation.search_form.model.SearchEvent
import com.ezam.melichallenge.search.presentation.search_form.model.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    // Representa el estado de la vista
    private val state = MutableStateFlow(SearchScreenState())

    // Proporciona el estado inmutable a la vista
    fun getState() = state.asStateFlow()


    // cambios en el campo de busqueda
    fun onSearchChange(newSearch: String) {
        state.update {
            it.copy( search = newSearch, isSearchButtonEnabled = validateSearchButton(newSearch) )
        }
    }

    // click al botón de busqueda
    fun onSearchClick() {
        state.update {
            it.copy( uiEvent = SearchEvent.SearchProducts(it.search))
        }
    }

    // decide cuando debe de habilitarse el boton de busqueda
    private fun validateSearchButton( search: String ) : Boolean {
        return search.isNotBlank()
    }

    // elimna el evento, indicando que ya fué consumido
    fun consumeUiEvent(){
        state.update {
            it.copy( uiEvent = null )
        }
    }
}