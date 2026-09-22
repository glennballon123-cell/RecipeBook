package com.example.recipebook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebook.components.RecipeItem
import com.example.recipebook.datamodel.AppState
import com.example.recipebook.datamodel.Recipe
import com.example.recipebook.datamodel.SampleData
import com.example.recipebook.ui.theme.Accent
import com.example.recipebook.ui.theme.OnSurface
import com.example.recipebook.ui.theme.PageBg

@Composable
fun RecipeBookApp(modifier: Modifier = Modifier) {
    val recipes = remember {
        mutableStateListOf<Recipe>().apply { addAll(SampleData.all) }
    }

    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
    var showAddSheet by remember { mutableStateOf(false) }

    selectedRecipe?.let { recipe ->
        RecipeDetailScreen(
            recipe = recipe,
            onBack = { selectedRecipe = null }
        )
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PageBg)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recipe Book",
                    style = MaterialTheme.typography.headlineSmall,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { showAddSheet = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Recipe",
                        tint = Accent,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        isSaved = AppState.isSaved(recipe.title),
                        onView = { selectedRecipe = recipe },
                        onToggleSave = { AppState.toggleSave(recipe.title) },
                        onDelete = { recipes.remove(recipe) }
                    )
                }
            }
        }

        if (showAddSheet) {
            AddRecipeSheet(
                onDismiss = { showAddSheet = false },
                onAdd = { newRecipe ->
                    recipes.add(newRecipe)
                    showAddSheet = false
                }
            )
        }
    }
}