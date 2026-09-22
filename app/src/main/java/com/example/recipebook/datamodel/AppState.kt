package com.example.recipebook.datamodel

import androidx.compose.runtime.mutableStateMapOf

object AppState {

    /** Saved recipe titles — observable so the UI updates instantly. */
    val saved = mutableStateMapOf<String, Boolean>()

    fun isSaved(title: String): Boolean = saved[title] == true

    fun toggleSave(title: String) {
        saved[title] = !(saved[title] ?: false)
    }

    fun savedTitles(): Set<String> = saved.filter { it.value }.keys

    /** Pantry ingredient keys — observable. true = user has it. */
    val pantry = mutableStateMapOf(
        "kanin" to true,
        "garlic" to true,
        "eggs" to true,
        "tuna" to true,
        "soy-sauce" to true,
        "vinegar" to true,
        "oil" to true,
        "calamansi" to true,
        "canton" to false,
        "pandesal" to false,
        "onion" to false,
        "chicken" to false,
        "butter" to false,
        "saba" to false
    )

    fun hasIngredient(key: String): Boolean = pantry[key] == true

    fun toggleIngredient(key: String) {
        pantry[key] = !(pantry[key] ?: false)
    }

    fun ownedPantryCount(): Int = pantry.count { it.value }

    val recipeRequirements: Map<String, Set<String>> = mapOf(
        "Chicken Adobo" to setOf("chicken", "soy-sauce", "vinegar", "garlic"),
        "Crispy Canned Tuna Sisig" to setOf("tuna", "garlic", "calamansi", "oil"),
        "Pancit Canton Supreme" to setOf("canton", "eggs", "garlic"),
        "Garlic Sinangag & Fried Egg" to setOf("kanin", "garlic", "eggs", "oil"),
        "Sweet Banana Maruya" to setOf("saba")
    )

    fun ownedCount(recipeTitle: String): Int =
        recipeRequirements[recipeTitle]?.count { hasIngredient(it) } ?: 0

    fun totalRequired(recipeTitle: String): Int =
        recipeRequirements[recipeTitle]?.size ?: 0

    fun canCook(recipeTitle: String): Boolean {
        val req = recipeRequirements[recipeTitle] ?: return false
        return req.isNotEmpty() && req.all { hasIngredient(it) }
    }
}