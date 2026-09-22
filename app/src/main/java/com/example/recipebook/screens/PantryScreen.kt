package com.example.recipebook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebook.datamodel.AppState
import com.example.recipebook.datamodel.SampleData
import com.example.recipebook.ui.theme.*

@Composable
fun PantryScreen() {
    // Reading AppState.pantry (a snapshot map) auto-triggers recomposition.
    val owned = AppState.ownedPantryCount()
    val readyCount = SampleData.all.count { AppState.canCook(it.title) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Pantry Cook",
            style = MaterialTheme.typography.headlineSmall,
            color = OnSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Check what you have in your dorm pantry",
            style = MaterialTheme.typography.bodySmall,
            color = SecondaryText,
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(Modifier.height(14.dp))

        Surface(
            color = AccentSoft,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "✓  $owned ingredients available · $readyCount meals ready",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentDeep,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(14.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        ChipGroup(
            title = "DRY STAPLES",
            items = listOf(
                "kanin" to "Kanin (Rice)", "garlic" to "Garlic", "eggs" to "Eggs",
                "tuna" to "Canned Tuna", "soy-sauce" to "Soy Sauce", "vinegar" to "Vinegar",
                "canton" to "Pancit Canton", "pandesal" to "Pandesal"
            )
        )

        Spacer(Modifier.height(14.dp))

        ChipGroup(
            title = "CHILLED & FRESH",
            items = listOf(
                "oil" to "Cooking Oil", "calamansi" to "Calamansi", "onion" to "Onion",
                "chicken" to "Chicken Thighs", "butter" to "Butter", "saba" to "Saba Bananas"
            )
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Meals You Can Cook Now",
            style = MaterialTheme.typography.titleMedium,
            color = OnSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))

        val ready = SampleData.all.filter { AppState.canCook(it.title) }
        if (ready.isEmpty()) {
            Text(
                "Toggle more pantry items to unlock recipes.",
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryText
            )
        } else {
            ready.forEach { r ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                r.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = OnSurface,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "₱${r.price}",
                                style = MaterialTheme.typography.titleSmall,
                                color = Accent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            "${AppState.ownedCount(r.title)} of ${AppState.totalRequired(r.title)} items owned",
                            style = MaterialTheme.typography.labelSmall,
                            color = AccentDeep
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun ChipGroup(
    title: String,
    items: List<Pair<String, String>>
) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.labelSmall,
            color = SecondaryText,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(6.dp))

        // Simple two-row wrap: use Column of Rows
        val chunked = items.chunked(3)
        chunked.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
            ) {
                rowItems.forEach { (key, label) ->
                    val active = AppState.hasIngredient(key)
                    Surface(
                        shape = CircleShape,
                        color = if (active) Accent else SurfaceMid,
                        onClick = { AppState.toggleIngredient(key) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (active) Color.White else OnSurface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                            maxLines = 1
                        )
                    }
                }
                // Fill remaining space if last row has fewer items
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}