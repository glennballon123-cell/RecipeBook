package com.example.recipebook.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.datamodel.Recipe
import com.example.recipebook.ui.theme.Accent
import com.example.recipebook.ui.theme.AccentDeep
import com.example.recipebook.ui.theme.AccentSoft
import com.example.recipebook.ui.theme.OnSurface
import com.example.recipebook.ui.theme.SecondaryText
import com.example.recipebook.ui.theme.SurfaceHigh

private val StarGold = Color(0xFFFFB300)

@Composable
fun RecipeItem(
    recipe: Recipe,
    isSaved: Boolean,
    onView: () -> Unit,
    onToggleSave: () -> Unit,
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onView
    ) {
        Column {
            // Image — prefer uploaded URI, else bundled drawable
            if (recipe.imageUri.isNotEmpty()) {
                AsyncImage(
                    model = recipe.imageUri,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                val imgRes = imageResFor(recipe.imageName)
                if (imgRes != 0) {
                    Image(
                        painter = painterResource(id = imgRes),
                        contentDescription = recipe.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleMedium,
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
                    text = recipe.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryText,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (recipe.minutes > 0) {
                        TagPill("⏱ ${recipe.minutes}m", AccentSoft, AccentDeep)
                    }
                    recipe.tags.take(2).forEach { tag ->
                        TagPill(tag, SurfaceHigh, SecondaryText)
                    }
                }

                Spacer(Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val starScale by animateFloatAsState(
                            targetValue = if (isSaved) 1.3f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            ),
                            label = "starScale"
                        )
                        IconButton(
                            onClick = onToggleSave,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isSaved) Icons.Filled.Star
                                else Icons.Outlined.StarBorder,
                                contentDescription = if (isSaved) "Unsave" else "Save",
                                tint = if (isSaved) StarGold else Accent,
                                modifier = Modifier
                                    .size(22.dp)
                                    .scale(starScale)
                            )
                        }
                        Text(
                            text = "${recipe.kcal} kcal · ${recipe.protein}g protein",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryText
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = SecondaryText,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
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
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
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