package com.ezam.yaperecipies.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.ezam.yaperecipies.presentation.model.Text

@Composable
@ReadOnlyComposable
fun stringText(text: Text): String {
    return when (text) {
        is Text.StringRes -> stringResource(id = text.id, *text.params)
        is Text.StringValue -> text.value
    }
}