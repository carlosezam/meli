package com.ezam.melichallenge.search.domain.repository

import arrow.core.Either
import com.ezam.melichallenge.search.domain.model.ProductDetails
import com.ezam.melichallenge.search.domain.repository.model.ProductDetailsError
import com.ezam.melichallenge.search.domain.repository.model.SearchPagination
import com.ezam.melichallenge.search.domain.repository.model.SearchProductError
import com.ezam.melichallenge.search.domain.repository.model.SearchProductResult

interface SearchRepository<T:SearchPagination> {

    /**
     * Realiza una búsqueda en el API con paginación.
     *
     * @param query Término de búsqueda que se utilizará para buscar en el API.
     * @param pagination Información de paginación que controla la solicitud de resultados adicionales.
     *                  Para la primera llamada, se puede pasar como null. Para solicitudes de
     *                  resultados adicionales, proporciona una instancia de SearchPagination.
     * @return Un objeto Either que representa el resultado de la búsqueda. Puede ser:
     *         - [Right]: Contiene un objeto SearchResult con los resultados de la búsqueda y
     *                   la información de paginación para la siguiente llamada.
     *         - [Left]: Contiene una Sealed Class SearchError si se produce un error durante la búsqueda.
     *
     * @see SearchPagination
     * @see SearchProductResult
     * @see SearchProductError
     */
    suspend fun searchProduct( query: String, pagination: T? = null) : Either<SearchProductError, SearchProductResult<T>>


    /**
     * Obtiene los detalles de un producto mediante su id
     *
     * @param productId Id del producto obtenido mediante searchProduct
     *
     * @return Un objeto Either que representa el resultado, puede ser:
     *         - [Right]: Contiene un objeto ProductDetails con los detalles del producto
     *         - [Left]: Contiene una Sealed Class ProductDetailsError con el error producido
     */
    suspend fun getProductDetails( productId: String ) : Either<ProductDetailsError, ProductDetails>
}