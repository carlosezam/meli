package com.ezam.yaperecipies.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.ezam.yaperecipies.presentation.model.Image

@Composable
fun painterImage(image: Image): Painter {
    return when (image) {
        is Image.DrawableRes -> painterResource(id = image.id)
        is Image.URL -> rememberAsyncImagePainter(model = image.url)
    }
}