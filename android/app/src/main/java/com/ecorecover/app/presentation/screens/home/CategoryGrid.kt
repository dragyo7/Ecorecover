package com.ecorecover.app.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecorecover.app.presentation.components.CategoryCard
import com.ecorecover.app.presentation.components.SectionTitle

data class Category(
    val emoji: String,
    val title: String
)

@Composable
fun CategoryGrid() {

    val categories = listOf(
        Category("📱","Phones"),
        Category("💻","Laptops"),
        Category("⌚","Smart Watches"),
        Category("🔋","Batteries"),
        Category("🖥","Desktop"),
        Category("📺","Television")
    )

    SectionTitle("Quick Categories")

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(categories) {

            CategoryCard(
                emoji = it.emoji,
                title = it.title
            )

        }

    }

}