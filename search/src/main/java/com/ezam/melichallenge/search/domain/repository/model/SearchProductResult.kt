package com.ezam.melichallenge.search.domain.repository.model

import com.ezam.melichallenge.search.domain.model.Product

/**
 * Clase que representa el resultado de una búsqueda de productos.
 *
 * @property results Una lista de objetos [Product] que contiene los resultados de la búsqueda.
 * @property next Información de paginación que indica cómo obtener los resultados adicionales.
 */
data class SearchProductResult<T:SearchPagination>(
    val results: List<Product>,
    val next: T,
)
