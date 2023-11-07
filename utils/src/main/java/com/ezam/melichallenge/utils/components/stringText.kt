package com.ezam.yaperecipies.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.ezam.yaperecipies.presentation.model.Text

/**
 * Obtiene una cadena de texto a partir de un objeto [Text].
 *
 * @param text El objeto [Text] que representa un texto, ya sea desde un recurso de cadena o un valor de cadena.
 * @return La cadena de texto correspondiente.
 *
 * Ejemplo de uso:
 * ```
 * val text: Text = Text.StringRes(R.string.my_text, arrayOf("param1", "param2"))
 * val result: String = stringText(text)
 * Text(text = result)
 * ```
 *
 * @note Esta función se puede utilizar en composables que se anotan como @Composable y @ReadOnlyComposable.
 */

@Composable
@ReadOnlyComposable
fun stringText(text: Text): String {
    return when (text) {
        is Text.StringRes -> stringResource(id = text.id, *text.params)
        is Text.StringValue -> text.value
    }
}