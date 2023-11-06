package com.ezam.melichallenge.search.presentation.search_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.search.presentation.search_list.components.SearchList
import com.ezam.melichallenge.search.presentation.search_list.components.SearchListEmpty
import com.ezam.melichallenge.search.presentation.search_list.components.SearchListError
import com.ezam.melichallenge.search.presentation.search_list.model.ResultItem
import com.ezam.melichallenge.search.presentation.search_list.model.SearchListState
import com.ezam.melichallenge.utils.components.SimpleSnackBar
import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text

@Composable
fun SearchListScreen(
    state: SearchListState,
    modifier: Modifier = Modifier,
    onResultItemClick: (ResultItem) -> Unit = {},
    onLoadMore: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (state.results == null && state.error == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .semantics { contentDescription = SearchListLoading }
                    .align(Alignment.Center),
            )
        } else if (state.results.isNullOrEmpty()) {
            if (state.error != null) {
                SearchListError(title = state.error, message = null)
            } else {
                SearchListEmpty()
            }
        } else {

            SearchList(
                state = state,
                onResultItemClick = onResultItemClick,
                onLoadMore = onLoadMore,
            )

            /*if (state.error != null) {
                SimpleSnackBar(
                    message = state.error,
                    modifier = Modifier
                        .semantics { contentDescription = SearchListSnackBar }
                        .align(Alignment.BottomCenter),
                    onSnackBarShown = onErrorShown
                )
            }*/


        }
    }
}


@Preview
@Composable
private fun SearchListScreenPreview() {
    val state = SearchListState(
        results = listOf(
            ResultItem(Image.DrawableRes(R.drawable.ic_search), Text.StringValue("lorem"))
        )
    )
    SearchListScreen(state)
}