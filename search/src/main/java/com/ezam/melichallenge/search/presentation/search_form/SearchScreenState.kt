package com.ezam.melichallenge.search.presentation.search_form

data class SearchScreenState(
    val search: String = "",
    val isSearchButtonEnabled: Boolean = false,
    val uiEvent: SearchEvent? = null,
)