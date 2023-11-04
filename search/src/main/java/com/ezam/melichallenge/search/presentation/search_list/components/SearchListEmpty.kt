package com.ezam.melichallenge.search.presentation.search_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ezam.melichallenge.search.R
import com.ezam.yaperecipies.presentation.component.stringText

@Composable
fun SearchListEmpty(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .semantics { contentDescription = "Sin resultados" }
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val searchingComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.face_searching))
        LottieAnimation(
            composition = searchingComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(120.dp),
            speed = 0.5f
        )
        Text(
            text = stringResource(R.string.label_no_encontramos_publicaciones),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.label_revisa_esta_bien_escrita),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun SearchListEmptyPreview() {
    SearchListEmpty()
}