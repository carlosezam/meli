package com.ezam.melichallenge.search.presentation.search_list.model

import com.ezam.yaperecipies.presentation.model.Text

data class SearchListState(
    val results: List<ResultItem>? = null,
    val error: Text? = null,
    val canLoadMoreData: Boolean = true,
)