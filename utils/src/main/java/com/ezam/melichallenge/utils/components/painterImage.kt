package com.ezam.yaperecipies.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.ezam.melichallenge.utils.models.Image

/**
 * Obtiene un [Painter] a partir de un objeto [Image] para su uso en un Composable.
 *
 * @param image El objeto [Image] que representa una imagen.
 * @return Un [Painter] que se puede utilizar para renderizar la imagen en un Composable.
 *
 * Ejemplo de uso:
 * ```
 * val image: Image = Image.DrawableRes(R.drawable.my_image)
 * val painter: Painter = painterImage(image)
 * Image(
 *     painter = painter,
 *     contentDescription = null, // Opcional
 *     modifier = Modifier.fillMaxSize()
 * )
 * ```
 */
@Composable
fun painterImage(image: Image): Painter {
    return when (image) {
        is Image.DrawableRes -> painterResource(id = image.id)
        is Image.URL -> rememberAsyncImagePainter(model = image.url)
    }
}