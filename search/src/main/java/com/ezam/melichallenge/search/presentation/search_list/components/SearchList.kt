package com.ezam.melichallenge.search.presentation.search_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezam.melichallenge.search.presentation.search_list.SearchListLoadingItem
import com.ezam.melichallenge.search.presentation.search_list.SearchListResultsScreen
import com.ezam.melichallenge.search.presentation.search_list.model.ResultItem
import com.ezam.melichallenge.search.presentation.search_list.model.SearchListState
import com.ezam.yaperecipies.presentation.component.stringText
import com.ezam.yaperecipies.presentation.model.Text
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
internal fun SearchList(
    state: SearchListState,
    modifier: Modifier = Modifier,
    onResultItemClick: (ResultItem) -> Unit = {},
    onLoadMore: () -> Unit = {},
) {

    val scrollState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier.semantics { contentDescription = SearchListResultsScreen },
        state = scrollState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(state.results ?: emptyList()) { resultItem ->
            SearchListItem(item = resultItem) {
                onResultItemClick(resultItem)
            }
        }


        if( state.error != null ){
            item {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringText(text = state.error))
                    Button(onClick = onLoadMore ) {
                        Text(text = "Reintentar")
                    }
                }
            }
        } else if (state.canLoadMoreData) {
            item {
                Box(
                    modifier = Modifier
                        .semantics { contentDescription = SearchListLoadingItem }
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(60.dp), strokeWidth = 5.dp)
                }
            }
        }
    }


    val shouldLoadMore = remember {
        derivedStateOf {
            val lastItem = scrollState.layoutInfo.totalItemsCount - 1
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastItem >= 0 && lastVisibleItem == lastItem
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && state.error == null ) {
            onLoadMore()
        }
    }
}

@Preview
@Composable
fun SearchListPreview() {
    SearchList(
        state = SearchListState( emptyList(), error = Text.StringValue("lorem") )
    )
}