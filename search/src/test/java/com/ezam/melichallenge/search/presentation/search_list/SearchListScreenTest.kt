package com.ezam.melichallenge.search.presentation.search_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ezam.melichallenge.search.R
import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text
import io.mockk.core.ValueClassSupport.boxedValue
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

    val emptyScreen
        get() = composeRule.onNodeWithContentDescription("Sin resultados")

    val errorScreen
        get() = composeRule.onNodeWithContentDescription("Error en la busqueda")

    val resultList
        get() = composeRule.onNodeWithContentDescription("Lista de resultados")

    val errorSnackBar
        get() = composeRule.onNodeWithContentDescription("SnackBar de error")

    @Test
    fun `pantalla sin resultados cuando la lista de resultados es vacia`() {
        //Given
        val state = SearchListState()
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
    fun `listado y snackbar cuando hay resultados, pero hay error al descargar mas resultados`() {
        //Given

        val state = SearchListState(
            results = listOf(
                ResultItem(
                    Image.DrawableRes(R.drawable.ic_search),
                    Text.StringValue("lorem")
                )
            ),
            error = Text.StringValue("Lorem")
        )
        composeRule.setContent { SearchListScreen(state = state) }

        //When

        //Then
        errorScreen.assertDoesNotExist()
        emptyScreen.assertDoesNotExist()

        resultList.assertIsDisplayed()
        errorSnackBar.assertIsDisplayed()
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