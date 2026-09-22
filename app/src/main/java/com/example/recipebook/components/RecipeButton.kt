package com.example.recipebook.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecipeButton(
    onButtonClick: () -> Unit,
    enable: Boolean,
    label: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onButtonClick,
        enabled = enable,
        modifier = modifier
    ) {
        Text(label)
    }
}