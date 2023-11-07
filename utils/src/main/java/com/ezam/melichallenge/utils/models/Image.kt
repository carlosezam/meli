package com.ezam.melichallenge.utils.models
import com.ezam.yaperecipies.presentation.component.painterImage


/**
 * Interfaz sellada que representa una imagen, que puede ser una imagen de recurso dibujable o una imagen
 * con una URL.
 *
 * @see painterImage Función para obtener un [Painter] a partir de un objeto [Image].
 */

sealed interface Image {

    /**
     * Representa una imagen a partir de un recurso dibujable identificado por su ID.
     */
    data class DrawableRes(val id: Int) : Image

    /**
     * Representa una imagen a partir de una URL.
     */
    data class URL(val url: String) : Image
}
