package com.example.recipebook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebook.datamodel.SampleData
import com.example.recipebook.ui.theme.*

@Composable
fun BudgetScreen() {
    var maxPrice by remember { mutableStateOf(100) }
    val filtered = SampleData.all.filter { it.price <= maxPrice }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .padding(16.dp)
    ) {
        Text(
            text = "Budget Meals",
            style = MaterialTheme.typography.headlineSmall,
            color = Accent,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Dorm-tested student recipes under ₱100",
            style = MaterialTheme.typography.bodySmall,
            color = SecondaryText,
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(Modifier.height(14.dp))

        // Comparison card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "AVERAGE DORM MEAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = AccentDeep
                )
                Text(
                    "₱45",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AccentDeep,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "CAMPUS CAFETERIA",
                    style = MaterialTheme.typography.labelSmall,
                    color = SecondaryText
                )
                Text(
                    "₱165",
                    style = MaterialTheme.typography.titleMedium,
                    color = SecondaryText,
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                )
                Spacer(Modifier.height(8.dp))
                Surface(
                    color = AccentSoft,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Save ~₱120 per meal  ·  73% cheaper",
                        style = MaterialTheme.typography.labelSmall,
                        color = AccentDeep,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // Filter buttons
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            FilterChipButton("All ₱100", maxPrice == 100) { maxPrice = 100 }
            FilterChipButton("Under ₱50", maxPrice == 50) { maxPrice = 50 }
            FilterChipButton("Under ₱30", maxPrice == 30) { maxPrice = 30 }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filtered, key = { it.id }) { r ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                r.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = AccentDeep,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "₱${r.price}",
                                style = MaterialTheme.typography.titleMedium,
                                color = OnSurface,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            r.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondaryText,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "ITEMIZED COST",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryText,
                            fontWeight = FontWeight.Bold
                        )
                        r.ingredients.forEach { ing ->
                            Text(
                                "  • ${ing.name} — ₱${ing.price}",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnSurfaceVar
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChipButton(label: String, active: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (active) Accent else SurfaceMid,
        onClick = onClick
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (active) Color.White else AccentDeep,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}