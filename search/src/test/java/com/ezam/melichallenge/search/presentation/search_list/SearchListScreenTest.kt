package com.ezam.melichallenge.search.presentation.search_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.search.presentation.search_list.model.ResultItem
import com.ezam.melichallenge.search.presentation.search_list.model.SearchListState
import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val loadingScreen
        get() = composeRule.onNodeWithContentDescription(SearchListLoading)

    private val emptyScreen
        get() = composeRule.onNodeWithContentDescription("Sin resultados")

    private val errorScreen
        get() = composeRule.onNodeWithContentDescription(SearchListErrorScreen)

    private val resultList
        get() = composeRule.onNodeWithContentDescription(SearchListResultsScreen)

    private val errorSnackBar
        get() = composeRule.onNodeWithContentDescription(SearchListSnackBar)

    @Test
    fun `pantalla de carga cuando la lista es nula`() {
        // given
        val state = SearchListState()
        composeRule.setContent { SearchListScreen(state = state) }

        // when

        // then
        loadingScreen.assertIsDisplayed()
    }

    @Test
    fun `pantalla sin resultados cuando la lista de resultados es vacia`() {
        //Given
        val state = SearchListState( results = emptyList())
        composeRule.setContent { SearchListScreen(state = state) }

        //When

        //Then
        emptyScreen.assertIsDisplayed()
        errorScreen.assertDoesNotExist()
        resultList.assertDoesNotExist()
    }

    @Test
    fun `pantalla de error cuando no hay resultados, solo error`() {
        //Given
        val state = SearchListState(
            results = emptyList(),
            error = Text.StringValue("Lorem")
        )
        composeRule.setContent { SearchListScreen(state = state) }

        //When

        //Then
        errorScreen.assertIsDisplayed()
        emptyScreen.assertDoesNotExist()
        resultList.assertDoesNotExist()
    }

    @Test
    fun `invoca onResultItemClick con el valor del item presionado`() {
        //Given
        val item1 = ResultItem(
            image = Image.URL("http://example.png"),
            name = Text.StringValue("Result 1")
        )
        val item2 = ResultItem(
            image = Image.URL("http://example.png"),
            name = Text.StringValue("Result 2")
        )
        val state = SearchListState(
            results = listOf(item1, item2)
        )
        val onResultItemClick = mockk<(ResultItem) -> Unit>(relaxed = true)

        composeRule.setContent {
            SearchListScreen(
                state = state,
                onResultItemClick = onResultItemClick
            )
        }

        //When
        composeRule.onNodeWithText("Result 2").performClick()

        //Then
        verify { onResultItemClick(item2) }
    }
}