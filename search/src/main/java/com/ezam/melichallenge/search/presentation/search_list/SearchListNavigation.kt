package com.ezam.melichallenge.search.presentation.search_list

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val SEARCH_LIST_ROUTE = "searchList/{search}"

fun NavController.navigateToSearchList(search: String) {
    navigate("searchList/$search")
}

fun NavGraphBuilder.searchListRoute(
    onShowDetails: ( ResultItem ) -> Unit
) {
    composable(
        route = SEARCH_LIST_ROUTE,
        arguments = listOf(
            navArgument("search"){ type = NavType.StringType }
        )
    ){

        val searchListViewModel = hiltViewModel<SearchListViewModel>()
        val state by searchListViewModel.getState().collectAsState()

        SearchListScreen(
            state = state,
            onResultItemClick = onShowDetails,
            onErrorShown = searchListViewModel::onErrorShown
        )
    }
}