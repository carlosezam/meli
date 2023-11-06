package com.ezam.melichallenge.search.presentation.search_form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezam.melichallenge.search.R
import com.ezam.melichallenge.search.presentation.search_form.model.SearchScreenState

@Composable
fun SearchScreen(
    state: SearchScreenState,
    modifier: Modifier = Modifier,
    onSearchChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {

        OutlinedTextField(
            value = state.search,
            onValueChange = onSearchChange,
            modifier = Modifier
                .semantics { contentDescription = SearchFormInput }
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search icon"
                )
            },
            singleLine = true
        )

        Button(
            onClick = onSearchClick,
            enabled = state.isSearchButtonEnabled,
            modifier = Modifier
                .semantics { contentDescription = SearchFormButton }
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Buscar ")
        }
    }
}


@Preview
@Composable
private fun SearchScreenPreview() {
    val state = SearchScreenState()
    SearchScreen(state)
}