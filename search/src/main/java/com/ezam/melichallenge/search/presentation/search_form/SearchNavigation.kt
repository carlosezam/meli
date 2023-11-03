package com.ezam.melichallenge.search.presentation.search_form

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SEARCH_ROUTE = "search"

fun NavGraphBuilder.searchRoute(
    onSearchRequest: (search: String) -> Unit
) {
    composable(SEARCH_ROUTE) {

        val searchViewModel = hiltViewModel<SearchViewModel>()
        val state by searchViewModel.getState().collectAsState()

        state.uiEvent?.let { uiEvent ->
            LaunchedEffect(key1 = uiEvent) {
                when( uiEvent ) {
                    is SearchEvent.SearchProducts -> {
                        onSearchRequest(uiEvent.search)
                        searchViewModel.consumeUiEvent()
                    }
                }
            }
        }

        SearchScreen(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onSearchChange = searchViewModel::onSearchChange,
            onSearchClick = searchViewModel::onSearchClick
        )

    }
}

fun NavController.navigateToSearch(){
    navigate("SEARCH_ROUTE")
}