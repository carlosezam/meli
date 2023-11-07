package com.ezam.melichallenge.search.presentation.details.model

import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.utils.models.Image
import com.ezam.yaperecipies.presentation.model.Text

data class DetailsState(
    val loading: Boolean = true,
    val error: Text? = null,
    val productName: String = "",
    val productImage: Image = Image.DrawableRes(R.drawable.no_image),
    val productDescription: List<String> = emptyList()
)