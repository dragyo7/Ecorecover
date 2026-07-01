package com.ecorecover.app.presentation.screens.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TrendChip(
    text: String,
    positive: Boolean
) {

    Text(
        text = text,
        modifier = Modifier
            .background(
                if (positive)
                    Color(0x2222C55E)
                else
                    Color(0x22EF4444),
                RoundedCornerShape(50)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
        color =
        if (positive)
            Color(0xFF22C55E)
        else
            Color.Red,
        style = MaterialTheme.typography.labelMedium
    )

}