package com.ezam.melichallenge.search.data.remote.mappers

import com.ezam.melichallenge.search.data.remote.model.SearchDTO
import com.ezam.melichallenge.search.domain.model.Product

fun SearchDTO.ResultDTO.toProduct() = Product(
    id = id,
    name = title,
    imageUrl = thumbnail,
    price = price.toString()
)