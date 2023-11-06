package com.ezam.melichallenge.search.presentation.search_list

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ezam.melichallenge.search.presentation.search_list.model.ResultItem

internal const val SEARCH_ARG = "search"
const val SEARCH_LIST_ROUTE = "searchList/{$SEARCH_ARG}"

fun NavController.navigateToSearchList(search: String) {
    navigate("searchList/$search")
}

fun NavGraphBuilder.searchListRoute(
    onShowDetails: (ResultItem) -> Unit
) {
    composable(
        route = SEARCH_LIST_ROUTE,
        arguments = listOf(
            navArgument("search"){ type = NavType.StringType }
        )
    ){

        val searchListViewModel = hiltViewModel<SearchListViewModel>()
        val state by searchListViewModel.getState().collectAsState()

        LaunchedEffect(key1 = Unit){
            searchListViewModel.loadNextPage()
        }

        SearchListScreen(
            state = state,
            onResultItemClick = onShowDetails,
            onLoadMore = {searchListViewModel.loadNextPage()},
        )
    }
}

internal class SearchListArgs( val search: String ) {
    constructor( savedStateHandle: SavedStateHandle ) : this (
        search = checkNotNull( savedStateHandle[SEARCH_ARG] )
    )
}