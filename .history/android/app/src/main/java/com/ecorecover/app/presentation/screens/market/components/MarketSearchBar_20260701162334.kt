package com.ecorecover.app.presentation.screens.market.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun MarketSearchBar() {

    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text("Search metals")
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                null
            )
        },
        singleLine = true
    )

}