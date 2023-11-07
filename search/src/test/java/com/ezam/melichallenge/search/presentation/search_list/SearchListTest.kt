package com.ezam.melichallenge.search.presentation.search_list

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import com.ezam.melichallenge.search.presentation.search_list.components.SearchList
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
class SearchListTest {

    @get:Rule
    val composeRule = createComposeRule()

    val searchList
        get() = composeRule.onNodeWithContentDescription(SearchListResultsScreen)

    @Test
    fun `no dede llamar a loadMore si ho ha llegado al fin de la lista`() {
        //given
        val results = List(50){ index -> ResultItem(id = "ID", image = Image.URL("$index"), name = Text.StringValue("$index") ) }

        val onLoadMore: () -> Unit = mockk(relaxed = true)

        composeRule.setContent { SearchList(state = SearchListState(results), onResultItemClick = {}, onLoadMore = onLoadMore) }

        //when
        searchList.performScrollToIndex(30)
        searchList.performScrollToIndex(10) // sube

        //then
        verify(exactly = 0) { onLoadMore() }
    }


    @Test
    fun `dede llamar a loadMore solo cuando llega al fin de la lista`() {
        //given
        val results = List(50){ index -> ResultItem(id = "ID",image = Image.URL("$index"), name = Text.StringValue("$index") ) }

        val onLoadMore: () -> Unit = mockk(relaxed = true)

        composeRule.setContent { SearchList(state = SearchListState(results), onResultItemClick = {}, onLoadMore = onLoadMore) }

        //when
        searchList.performScrollToIndex(40)
        searchList.performScrollToIndex(10) // sube
        searchList.performScrollToIndex(50) // baja pero no llega al final
        composeRule.waitForIdle()

        //then
        verify(exactly = 1) { onLoadMore() }
    }
}