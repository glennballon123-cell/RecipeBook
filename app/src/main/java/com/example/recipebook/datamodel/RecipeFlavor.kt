package com.example.recipebook.datamodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

enum class Flavor(val color: Color) {
    SAVORY(Color(0xFF388E3C)),
    SWEET(Color(0xFFD32F2F)),

}

data class Ingredient(
    val name: String,
    val quantity: String,
    val price: Int
)

data class Step(
    val title: String,
    val minutes: Int,
    val detail: String
)

data class Recipe(
    val id: Long = System.nanoTime(),
    val title: String,
    val description: String,
    val flavor: Flavor,
    val price: Int = 0,
    val minutes: Int = 0,
    val kcal: Int = 0,
    val protein: Int = 0,
    val imageName: String = "",
    val imageUri: String = "",
    val tags: List<String> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<Step> = emptyList()
)

class IngredientInput {
    var name by mutableStateOf("")
    var quantity by mutableStateOf("")
    var price by mutableStateOf("")
}

class StepInput {
    var title by mutableStateOf("")
    var minutes by mutableStateOf("")
    var detail by mutableStateOf("")
}