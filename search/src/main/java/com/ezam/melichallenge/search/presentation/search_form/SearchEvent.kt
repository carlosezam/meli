package com.ezam.melichallenge.search.presentation.search_form

sealed interface SearchEvent {
    data class SearchProducts( val search: String ) : SearchEvent
}