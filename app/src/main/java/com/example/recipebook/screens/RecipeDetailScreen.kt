package com.example.recipebook.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebook.datamodel.Recipe
import com.example.recipebook.ui.theme.*
import coil.compose.AsyncImage

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    onStartCooking: () -> Unit = {},
    onStopCooking: () -> Unit = {}
) {
    var cooking by remember { mutableStateOf(false) }
    val checkedIngredients = remember { mutableStateListOf<Boolean>() }

    // Initialize checkbox state once
    LaunchedEffect(recipe.id) {
        checkedIngredients.clear()
        repeat(recipe.ingredients.size) { checkedIngredients.add(false) }
    }

    val checkedCount = checkedIngredients.count { it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .verticalScroll(rememberScrollState())
    ) {
        // Top bar with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = OnSurface
                )
            }
            Text(
                text = "Recipe Detail",
                style = MaterialTheme.typography.titleMedium,
                color = Accent,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            // Image
            if (recipe.imageUri.isNotEmpty()) {
                AsyncImage(
                    model = recipe.imageUri,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(14.dp))
            } else {
                val imgRes = imageResFor(recipe.imageName)
                if (imgRes != 0) {
                    Image(
                        painter = painterResource(id = imgRes),
                        contentDescription = recipe.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(18.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(14.dp))
                }
            }

            // Title + price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (recipe.price > 0) {
                    Text(
                        text = "₱${recipe.price}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Accent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "₱${recipe.price} per serving · ${recipe.minutes} mins · 1 Pot",
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryText,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(Modifier.height(10.dp))

            // Tag row
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                TagPill("Easy", SurfaceHigh, OnSurfaceVar)
                TagPill("1–2", SurfaceHigh, OnSurfaceVar)
                TagPill("Induction Safe", SurfaceHigh, OnSurfaceVar)
            }

            Spacer(Modifier.height(16.dp))

            // Ingredients card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$checkedCount/${recipe.ingredients.size} checked",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryText
                        )
                    }
                    Spacer(Modifier.height(8.dp))

                    recipe.ingredients.forEachIndexed { index, ing ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checkedIngredients.getOrElse(index) { false },
                                onCheckedChange = { checked ->
                                    if (index < checkedIngredients.size) {
                                        checkedIngredients[index] = checked
                                    }
                                }
                            )
                            Text(
                                text = "${ing.name} — ${ing.quantity}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurface,
                                modifier = Modifier.weight(1f)
                            )
                            if (ing.price > 0) {
                                Text(
                                    text = "₱${ing.price}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SecondaryText
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // Total
                    Surface(
                        color = AccentSoft,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Estimated Cost",
                                style = MaterialTheme.typography.labelSmall,
                                color = AccentDeep,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "₱${recipe.price}.00",
                                style = MaterialTheme.typography.titleMedium,
                                color = Accent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Instructions card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${recipe.steps.size} steps",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryText
                        )
                    }
                    Spacer(Modifier.height(10.dp))

                    recipe.steps.forEachIndexed { index, step ->
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = AccentSoft2,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${index + 1}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AccentDeep,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${step.title} · ${step.minutes} min",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnSurface,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = step.detail,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnSurfaceVar
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Pro tip
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = AccentSoft),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "Pro Dorm Tip: Use leftover pan glaze and cold rice tomorrow for adobo fried rice.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVar,
                    modifier = Modifier.padding(14.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Start / Stop Cooking
            Button(
                onClick = {
                    cooking = !cooking
                    if (cooking) onStartCooking() else onStopCooking()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (cooking) "Stop Cooking (${recipe.minutes}m)" else "Start Cooking",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun TagPill(text: String, bg: Color, fg: Color) {
    Surface(shape = CircleShape, color = bg) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = fg,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun imageResFor(name: String): Int {
    return when (name) {
        "adobo.jpg", "adobo" -> com.example.recipebook.R.drawable.adobo
        "canton.jpg", "canton" -> com.example.recipebook.R.drawable.canton
        "maruya.jpg", "maruya" -> com.example.recipebook.R.drawable.maruya
        "sisig.jpg", "sisig" -> com.example.recipebook.R.drawable.sisig
        "sinangag.jpg", "sinangag" -> com.example.recipebook.R.drawable.sinangag
        else -> 0
    }
}