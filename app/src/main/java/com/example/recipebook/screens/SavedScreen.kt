package com.example.recipebook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.recipebook.components.RecipeItem
import com.example.recipebook.datamodel.AppState
import com.example.recipebook.datamodel.Recipe
import com.example.recipebook.datamodel.SampleData
import com.example.recipebook.ui.theme.OnSurface
import com.example.recipebook.ui.theme.PageBg
import com.example.recipebook.ui.theme.SecondaryText

@Composable
fun SavedScreen(onOpenRecipe: (Recipe) -> Unit) {
    // Reading the observable snapshot map triggers recomposition on change
    val savedTitles = AppState.savedTitles()
    val savedRecipes = SampleData.all.filter { savedTitles.contains(it.title) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .padding(16.dp)
    ) {
        Text(
            text = "Saved Recipes",
            style = MaterialTheme.typography.headlineSmall,
            color = OnSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Tap ☆ on any recipe to save it here",
            style = MaterialTheme.typography.bodySmall,
            color = SecondaryText,
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(Modifier.height(14.dp))

        if (savedRecipes.isEmpty()) {
            Text(
                text = "Nothing saved yet.\nBrowse Recipes and tap ☆ to bookmark a dish.",
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(savedRecipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        isSaved = true,
                        onView = { onOpenRecipe(recipe) },
                        onToggleSave = { AppState.toggleSave(recipe.title) },
                        onDelete = {}
                    )
                }
            }
        }
    }
}