package com.ezam.yaperecipies.presentation.model

/**
 * Interfaz sellada que representa texto, que puede ser un recurso o un valor de literal.
 */
sealed interface Text {

    /**
     * Representa una cadena de recurso con identificación y parámetros opcionales.
     */
    data class StringRes(val id: Int, val params: Array<Any> = emptyArray()) : Text {

        /**
         * Comprueba si otro objeto es igual tomando en cuenta el contenido de [params]
         */
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StringRes

            if (id != other.id) return false
            if (!params.contentEquals(other.params)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + params.contentHashCode()
            return result
        }
    }

    /**
     * Representa una string literal.
     */
    data class StringValue(val value: String) : Text
}