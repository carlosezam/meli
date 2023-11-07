package com.ezam.melichallenge.search.presentation.details

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ezam.melichallenge.search.presentation.components.ErrorScreen
import com.ezam.melichallenge.search.presentation.components.LoadingScreen
import com.ezam.melichallenge.search.presentation.details.model.DetailsState
import com.ezam.yaperecipies.presentation.component.painterImage

@Composable
fun DetailsScreen(state: DetailsState, modifier: Modifier = Modifier) {

    if (state.loading) {
        LoadingScreen()
    } else if(state.error != null) {
        ErrorScreen(title = state.error, message = null)
    }else {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Image(
                painter = painterImage(image = state.productImage),
                contentDescription = DetailsImage,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Text(
                text = state.productName,
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp), style = MaterialTheme.typography.headlineSmall
            )

            val dotColor = MaterialTheme.colorScheme.surfaceVariant

            state.productDescription.forEach {
                Row(modifier = Modifier.padding(4.dp)) {

                    Text(
                        text = "  ",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .drawBehind {
                                drawCircle(color = dotColor, radius = size.maxDimension / 3f)
                            }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = it, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}