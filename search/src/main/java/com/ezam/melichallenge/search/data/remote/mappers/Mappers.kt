package com.ezam.melichallenge.search.data.remote.mappers

import com.ezam.melichallenge.search.data.remote.model.DetailsDTO
import com.ezam.melichallenge.search.data.remote.model.SearchDTO
import com.ezam.melichallenge.search.domain.model.Product
import com.ezam.melichallenge.search.domain.model.ProductAttribute
import com.ezam.melichallenge.search.domain.model.ProductDetails

fun SearchDTO.ResultDTO.toProduct() = Product(
    id = id,
    name = title,
    imageUrl = thumbnail,
    price = price.toString()
)

fun DetailsDTO.toProductDetails() = ProductDetails(
    id = id,
    name = title,
    imageUrls = pictures.map { it.secureUrl },
    attributes = attributes.map { ProductAttribute( name = it.name, value = it.valueName) }
)