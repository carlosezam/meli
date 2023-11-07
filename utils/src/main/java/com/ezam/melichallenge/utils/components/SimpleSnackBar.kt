package com.ezam.melichallenge.utils.components

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ezam.yaperecipies.presentation.component.stringText
import com.ezam.yaperecipies.presentation.model.Text

/**
 * Muestra un Snackbar simple con un mensaje de texto y una acción personalizada.
 *
 * @param message El mensaje de texto que se mostrará en el Snackbar.
 * @param modifier [Modifier] para personalizar la apariencia y el comportamiento del Snackbar.
 * @param onSnackBarShown Una lambda que se ejecutará cuando el Snackbar se muestre.
 *
 * Ejemplo de uso:
 * ```
 * SimpleSnackBar(
 *     message = Text.StringRes(R.string.snackbar_message),
 *     modifier = Modifier.padding(16.dp),
 *     onSnackBarShown = {
 *         // Realizar acciones cuando se muestra el Snackbar
 *     }
 * )
 * ```
 *
 * Esta función simplifica la creación de un Snackbar básico, evitanndo la complejidad
 * de una implementación completa y permitiendo personalizar el mensaje y la acción a realizar.
 */
@Composable
fun SimpleSnackBar(message: Text, modifier: Modifier = Modifier, onSnackBarShown: () -> Unit) {
    val stringMessage = stringText(text = message)
    val hostState = remember { SnackbarHostState() }

    SnackbarHost( hostState = hostState, modifier = modifier)

    LaunchedEffect(key1 = stringMessage){
        hostState.showSnackbar(stringMessage)
        onSnackBarShown()
    }

}