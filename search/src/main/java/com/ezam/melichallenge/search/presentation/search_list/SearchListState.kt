package com.ezam.melichallenge.search.presentation.search_list

import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text

data class SearchListState(
    val results: List<ResultItem> = emptyList(),
    val error: Text? = null
)

data class ResultItem(
    val image: Image,
    val name: Text
)