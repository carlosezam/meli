package com.ezam.melichallenge.search.presentation.search_list.model

import com.ezam.melichallenge.search.domain.model.Product
import com.ezam.melichallenge.utils.models.Image
import com.ezam.yaperecipies.presentation.model.Text

fun Product.toResultItem() = ResultItem(
    id=id,
    image = Image.URL(imageUrl),
    name = Text.StringValue(name)
)