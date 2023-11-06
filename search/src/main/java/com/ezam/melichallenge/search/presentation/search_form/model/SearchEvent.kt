package com.ezam.melichallenge.search.presentation.search_form.model

sealed interface SearchEvent {
    data class SearchProducts( val search: String ) : SearchEvent
}