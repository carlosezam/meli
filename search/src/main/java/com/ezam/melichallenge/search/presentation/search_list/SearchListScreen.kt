package com.ezam.melichallenge.search.presentation.search_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.search.presentation.search_list.components.SearchListEmpty
import com.ezam.melichallenge.search.presentation.search_list.components.SearchListError
import com.ezam.melichallenge.search.presentation.search_list.components.SearchListItem
import com.ezam.melichallenge.utils.components.SimpleSnackBar
import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text

@Composable
fun SearchListScreen(
    state: SearchListState,
    modifier: Modifier = Modifier,
    onResultItemClick: (ResultItem) -> Unit = {},
    onErrorShown: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()){
        if (state.results.isEmpty()) {
            if (state.error != null) {
                SearchListError(title = state.error, message = null)
            } else {
                SearchListEmpty()
            }
        } else {
            SearchList(results = state.results, onResultItemClick)

            if (state.error != null) {
                SimpleSnackBar(
                    message = state.error,
                    modifier = Modifier
                        .semantics { contentDescription = "SnackBar de error" }
                        .align(Alignment.BottomCenter),
                    onSnackBarShown = onErrorShown
                )
            }
        }
    }
}


@Composable
private fun SearchList(results: List<ResultItem>, onResultItemClick: (ResultItem) -> Unit, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier.semantics { contentDescription = "Lista de resultados" },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(results) { resultItem ->
            SearchListItem(item = resultItem) {
                onResultItemClick(resultItem)
            }
        }
    }
}

@Preview
@Composable
private fun SearchListScreenPreview() {
    val state = SearchListState(
        results = listOf(
            ResultItem( Image.DrawableRes(R.drawable.ic_search), Text.StringValue("lorem"))
        )
    )
    SearchListScreen(state)
}