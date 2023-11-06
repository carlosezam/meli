package com.ezam.melichallenge.search.presentation.search_list.model

import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text

data class ResultItem(
    val image: Image,
    val name: Text
)