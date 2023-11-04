package com.ezam.melichallenge.utils.components

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ezam.yaperecipies.presentation.component.stringText
import com.ezam.yaperecipies.presentation.model.Text

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