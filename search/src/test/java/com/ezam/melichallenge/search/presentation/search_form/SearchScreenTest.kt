package com.ezam.melichallenge.search.presentation.search_form

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.ezam.melichallenge.search.presentation.search_form.model.SearchScreenState
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchScreenTest {

    @get:Rule
    val composeRule = createComposeRule()


    private val searchField
        get() = composeRule.onNodeWithContentDescription("Search input")

    private val searchButton
        get() = composeRule.onNodeWithContentDescription("Search button")

    @Test
    fun `boton activado de acuerdo al state`() {
        //given
        val state = SearchScreenState( isSearchButtonEnabled = true )
        composeRule.setContent { SearchScreen(state = state) }

        //when

        //then
        searchButton.assertIsEnabled()
    }

    @Test
    fun `boton desactivado de acuerdo al state`() {
        //given
        val state = SearchScreenState( isSearchButtonEnabled = false )
        composeRule.setContent { SearchScreen(state = state) }

        //when

        //then
        searchButton.assertIsNotEnabled()
    }
}