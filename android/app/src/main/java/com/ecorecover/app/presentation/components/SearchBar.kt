package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

@Composable
fun SearchBar() {

    var text by remember {

        mutableStateOf("")

    }

    OutlinedTextField(

        value = text,

        onValueChange = {

            text = it

        },

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),

        placeholder = {

            Text("Search your device")

        },

        leadingIcon = {

            Icon(
                Icons.Default.Search,
                null
            )

        }

    )

}