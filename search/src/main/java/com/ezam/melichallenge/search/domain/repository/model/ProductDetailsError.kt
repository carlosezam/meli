package com.ezam.melichallenge.search.domain.repository.model

/**
 * Interfaz sellada que representa los posibles errores relacionados con la búsqueda de productos.
 */
sealed interface ProductDetailsError {
    /**
     *
     */
    object NotFound: ProductDetailsError

    /**
     * Error que indica una falta de conexión a Internet durante la búsqueda de productos.
     */
    object InternetError: ProductDetailsError

    /**
     * Error genérico desconocido relacionado con la búsqueda de productos.
     */
    object UnknownError: ProductDetailsError
}


