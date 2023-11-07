package com.ezam.melichallenge.search.domain.model

data class ProductDetails(
    val id: String,
    val name: String,
    val imageUrls: List<String>,
    val attributes: List<ProductAttribute>,
)
