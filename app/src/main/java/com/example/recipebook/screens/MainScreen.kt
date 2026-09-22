package com.example.recipebook.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.recipebook.datamodel.Recipe
import com.example.recipebook.ui.theme.Accent
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    selectedRecipe?.let { recipe ->
        RecipeDetailScreen(
            recipe = recipe,
            onBack = { selectedRecipe = null }
        )
        return
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavTab("Recipes", Icons.Default.Book,
                    pagerState.currentPage == 0) {
                    scope.launch { pagerState.animateScrollToPage(0) }
                }
                NavTab("Budget", Icons.Default.Wallet,
                    pagerState.currentPage == 1) {
                    scope.launch { pagerState.animateScrollToPage(1) }
                }
                NavTab("Pantry", Icons.Default.Inventory2,
                    pagerState.currentPage == 2) {
                    scope.launch { pagerState.animateScrollToPage(2) }
                }
                NavTab("Saved", Icons.Default.Bookmark,
                    pagerState.currentPage == 3) {
                    scope.launch { pagerState.animateScrollToPage(3) }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> RecipeBookApp()
                1 -> BudgetScreen()
                2 -> PantryScreen()
                3 -> SavedScreen(onOpenRecipe = { selectedRecipe = it })
            }
        }
    }
}

@Composable
private fun RowScope.NavTab(
    label: String,
    icon: ImageVector,
    active: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = active,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = {
            Text(
                label,
                fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Accent,
            selectedTextColor = Accent,
            indicatorColor = Accent.copy(alpha = 0.12f)
        )
    )
}