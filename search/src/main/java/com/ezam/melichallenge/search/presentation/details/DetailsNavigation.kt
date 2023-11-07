package com.ezam.melichallenge.search.presentation.details

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

internal const val PRODUCT_ID = "productId"
const val DETAILS_ROUE = "details/{$PRODUCT_ID}"

fun NavController.navigateToDetails(productId: String){
    navigate("details/$productId")
}
fun NavGraphBuilder.detailsRoute(){

    composable(
        route = DETAILS_ROUE,
        arguments = listOf(
            navArgument(PRODUCT_ID){ type = NavType.StringType}
        )
    ){

        val detailsViewModel = hiltViewModel<DetailsViewModel>()
        val state by detailsViewModel.getState().collectAsState()

        LaunchedEffect(key1 = Unit){
            detailsViewModel.loadDetails()
        }

        DetailsScreen(state = state)
    }
}

internal class DetailsArgs( val productId: String){
    constructor(savedStateHandle: SavedStateHandle  ) : this (
        productId = savedStateHandle[PRODUCT_ID] ?: ""
    )
}



