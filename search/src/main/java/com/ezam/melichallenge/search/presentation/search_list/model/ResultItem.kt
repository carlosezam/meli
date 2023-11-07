package com.ezam.melichallenge.search.presentation.search_list.model

import com.ezam.melichallenge.utils.models.Image
import com.ezam.yaperecipies.presentation.model.Text

data class ResultItem(
    val id: String,
    val image: Image,
    val name: Text
)