package com.ezam.melichallenge.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ezam.melichallenge.search.presentation.details.detailsRoute
import com.ezam.melichallenge.search.presentation.details.navigateToDetails
import com.ezam.melichallenge.search.presentation.search_form.SEARCH_ROUTE
import com.ezam.melichallenge.search.presentation.search_form.navigateToSearch
import com.ezam.melichallenge.search.presentation.search_form.searchRoute
import com.ezam.melichallenge.search.presentation.search_list.navigateToSearchList
import com.ezam.melichallenge.search.presentation.search_list.searchListRoute

@Composable
fun MeliChallengeNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SEARCH_ROUTE ){
        searchRoute(
            onSearchRequest = { navController.navigateToSearchList(it) }
        )
        searchListRoute(
            onShowDetails = { navController.navigateToDetails(it.id) }
        )
        detailsRoute()
    }
}