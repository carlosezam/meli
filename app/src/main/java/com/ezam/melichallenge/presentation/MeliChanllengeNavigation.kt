package com.ezam.melichallenge.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ezam.melichallenge.search.presentation.search_form.SEARCH_ROUTE
import com.ezam.melichallenge.search.presentation.search_form.searchRoute

@Composable
fun MeliChallengeNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SEARCH_ROUTE ){
        searchRoute( {} )
    }
}