package com.ezam.melichallenge.search.presentation.search_form.model

data class SearchScreenState(
    val search: String = "",
    val isSearchButtonEnabled: Boolean = false,
    val uiEvent: SearchEvent? = null,
)