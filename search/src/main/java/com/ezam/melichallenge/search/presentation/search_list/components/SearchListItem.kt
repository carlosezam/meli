package com.ezam.melichallenge.search.presentation.search_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezam.melichallenge.search.presentation.search_list.model.ResultItem
import com.ezam.yaperecipies.presentation.component.painterImage
import com.ezam.yaperecipies.presentation.component.stringText
import com.ezam.yaperecipies.presentation.model.Image
import com.ezam.yaperecipies.presentation.model.Text


@Composable
fun SearchListItem(item: ResultItem, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {

    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            )
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterImage(image = item.image),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
                .clip( RoundedCornerShape(4.dp))

        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 4.dp, bottom = 8.dp, start = 20.dp)
                .weight(1f)
        ) {
            Text(
                text = stringText(text = item.name),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
private fun SearchListItemPreview() {
    val item = ResultItem(
        id = "ID",
        image = Image.URL("http://http2.mlstatic.com/D_874210-MLA69633547567_052023-I.jpg"),
        name = Text.StringValue("Lorem ipsum")
    )
    SearchListItem(
        item = item
    )
}