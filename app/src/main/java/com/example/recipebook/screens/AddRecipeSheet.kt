package com.example.recipebook.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebook.datamodel.*
import com.example.recipebook.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeSheet(
    onDismiss: () -> Unit,
    onAdd: (Recipe) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var flavor by remember { mutableStateOf(Flavor.SAVORY) }
    var price by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var kcal by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }

    val ingredients = remember { mutableStateListOf(IngredientInput(), IngredientInput()) }
    val steps = remember { mutableStateListOf(StepInput(), StepInput()) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri.toString()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Add Recipe",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Close", tint = AccentDeep)
                }
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Recipe Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Text("Flavor", style = MaterialTheme.typography.labelSmall, color = SecondaryText)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FlavorPill("Savory", flavor == Flavor.SAVORY) { flavor = Flavor.SAVORY }
                FlavorPill("Sweet",  flavor == Flavor.SWEET)  { flavor = Flavor.SWEET }
            }

            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SmallNumberField("Price ₱", price, { price = it }, Modifier.weight(1f))
                SmallNumberField("Minutes", minutes, { minutes = it }, Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SmallNumberField("Kcal", kcal, { kcal = it }, Modifier.weight(1f))
                SmallNumberField("Protein g", protein, { protein = it }, Modifier.weight(1f))
            }

            Spacer(Modifier.height(14.dp))

            // IMAGE SECTION
            Text("Image", style = MaterialTheme.typography.labelSmall, color = SecondaryText)
            Spacer(Modifier.height(6.dp))

            // Preview if a URI is chosen
            if (imageUri.isNotEmpty()) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(6.dp))
                TextButton(onClick = { imageUri = "" }) {
                    Icon(Icons.Default.Delete, null, tint = ErrorRed, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Remove image", color = ErrorRed)
                }
            }

            // The only image input — pick from gallery
            Button(
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentSoft,
                    contentColor = AccentDeep
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Image, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Pick image from Gallery", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            // INGREDIENTS
            Text(
                "INGREDIENTS",
                style = MaterialTheme.typography.labelSmall,
                color = SecondaryText,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            ingredients.forEachIndexed { index, ing ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = ing.name,
                        onValueChange = { ing.name = it },
                        placeholder = { Text("Name", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier.weight(1.6f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = ing.quantity,
                        onValueChange = { ing.quantity = it },
                        placeholder = { Text("Qty", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = ing.price,
                        onValueChange = { ing.price = it },
                        placeholder = { Text("₱", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier.weight(0.9f),
                        singleLine = true
                    )
                    IconButton(
                        onClick = { if (ingredients.size > 1) ingredients.removeAt(index) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Default.Delete, "Remove", tint = SecondaryText, modifier = Modifier.size(18.dp))
                    }
                }
                Spacer(Modifier.height(4.dp))
            }
            TextButton(onClick = { ingredients.add(IngredientInput()) }) {
                Icon(Icons.Default.Add, null, tint = Accent, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text("Add ingredient", color = Accent, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            // INSTRUCTIONS
            Text(
                "INSTRUCTIONS",
                style = MaterialTheme.typography.labelSmall,
                color = SecondaryText,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            steps.forEachIndexed { index, s ->
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = s.title,
                            onValueChange = { s.title = it },
                            placeholder = { Text("Step title", style = MaterialTheme.typography.bodySmall) },
                            modifier = Modifier.weight(2f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = s.minutes,
                            onValueChange = { s.minutes = it },
                            placeholder = { Text("min", style = MaterialTheme.typography.bodySmall) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        IconButton(
                            onClick = { if (steps.size > 1) steps.removeAt(index) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Delete, "Remove", tint = SecondaryText, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = s.detail,
                        onValueChange = { s.detail = it },
                        placeholder = { Text("Instruction detail", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(10.dp))
            }
            TextButton(onClick = { steps.add(StepInput()) }) {
                Icon(Icons.Default.Add, null, tint = Accent, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text("Add step", color = Accent, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(18.dp))

            Button(
                onClick = {
                    val builtIngredients = ingredients
                        .filter { it.name.isNotBlank() }
                        .map {
                            Ingredient(
                                name = it.name.trim(),
                                quantity = it.quantity.trim().ifBlank { "—" },
                                price = it.price.trim().toIntOrNull() ?: 0
                            )
                        }
                    val builtSteps = steps
                        .filter { it.title.isNotBlank() || it.detail.isNotBlank() }
                        .map {
                            Step(
                                title = it.title.trim().ifBlank { "Step" },
                                minutes = it.minutes.trim().toIntOrNull() ?: 0,
                                detail = it.detail.trim()
                            )
                        }
                    val recipe = Recipe(
                        title = title.trim(),
                        description = description.trim(),
                        flavor = flavor,
                        price = price.trim().toIntOrNull() ?: 0,
                        minutes = minutes.trim().toIntOrNull() ?: 0,
                        kcal = kcal.trim().toIntOrNull() ?: 0,
                        protein = protein.trim().toIntOrNull() ?: 0,
                        imageName = "",
                        imageUri = imageUri,
                        tags = emptyList(),
                        ingredients = builtIngredients,
                        steps = builtSteps
                    )
                    onAdd(recipe)
                },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Recipe", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun FlavorPill(label: String, active: Boolean, onClick: () -> Unit) {
    Surface(
        shape = CircleShape,
        color = if (active) Accent else SurfaceMid,
        onClick = onClick
    ) {
        Text(
            label,
            color = if (active) Color.White else OnSurface,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun SmallNumberField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        modifier = modifier,
        singleLine = true
    )
}