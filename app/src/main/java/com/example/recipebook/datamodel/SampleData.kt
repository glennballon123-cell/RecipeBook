package com.example.recipebook.datamodel

object SampleData {

    val all: List<Recipe> = listOf(
        Recipe(
            id = 1L,
            title = "Chicken Adobo",
            description = "Savory, garlicky, vinegary. Keeps 4 days in a mini-fridge.",
            flavor = Flavor.SAVORY,
            price = 85, minutes = 25, kcal = 380, protein = 28,
            imageName = "adobo",
            tags = listOf("4 items", "1 Pot"),
            ingredients = listOf(
                Ingredient("Chicken thighs", "250g", 55),
                Ingredient("Garlic, crushed", "3 cloves", 5),
                Ingredient("Soy sauce", "3 tbsp", 5),
                Ingredient("Cane vinegar", "2 tbsp", 4),
                Ingredient("Dried bay leaf", "1 pc", 3),
                Ingredient("Peppercorns", "1/2 tsp", 2),
                Ingredient("White rice", "1 cup", 10)
            ),
            steps = listOf(
                Step("Sear garlic & chicken", 4, "Brown chicken skin-side down with garlic."),
                Step("Add soy & peppercorns", 5, "Add soy, peppercorns, bay leaf. Simmer."),
                Step("Add cane vinegar", 2, "Pour vinegar. Do NOT stir for 2 min."),
                Step("Simmer & reduce", 12, "Cover, low heat, reduce to glossy glaze."),
                Step("Serve & enjoy", 2, "Ladle over warm white rice.")
            )
        ),
        Recipe(
            id = 2L,
            title = "Crispy Canned Tuna Sisig",
            description = "Pan-crisped spicy tuna with onions and citrus tang.",
            flavor = Flavor.SAVORY,
            price = 55, minutes = 8, kcal = 290, protein = 26,
            imageName = "sisig",
            tags = listOf("High Protein", "1-Pan"),
            ingredients = listOf(
                Ingredient("Canned tuna in oil", "155g", 38),
                Ingredient("Red onion", "1/2 small", 7),
                Ingredient("Calamansi & siling labuyo", "—", 10)
            ),
            steps = listOf(
                Step("Sizzle", 3, "Sauté onion, add drained tuna."),
                Step("Crisp", 3, "Press flat, let it crisp on high heat."),
                Step("Finish", 2, "Squeeze calamansi, toss chili.")
            )
        ),
        Recipe(
            id = 3L,
            title = "Pancit Canton Supreme",
            description = "Upgraded chilimansi noodles with a jammy egg.",
            flavor = Flavor.SAVORY,
            price = 38, minutes = 6, kcal = 420, protein = 11,
            imageName = "canton",
            tags = listOf("Late Night", "Fast Prep"),
            ingredients = listOf(
                Ingredient("Chilimansi canton pack", "1 pack", 20),
                Ingredient("Fresh egg", "1 pc", 9),
                Ingredient("Toasted garlic & scallion", "—", 9)
            ),
            steps = listOf(
                Step("Boil", 3, "Boil noodles, drain, reserve water."),
                Step("Toss", 2, "Toss with seasoning and toppings."),
                Step("Egg", 1, "Top with jammy fried egg.")
            )
        ),
        Recipe(
            id = 4L,
            title = "Garlic Sinangag & Fried Egg",
            description = "Golden toasted garlic crisps with a sunny egg.",
            flavor = Flavor.SAVORY,
            price = 35, minutes = 12, kcal = 380, protein = 14,
            imageName = "sinangag",
            tags = listOf("Stove/Pan", "Dorm Staple"),
            ingredients = listOf(
                Ingredient("Leftover white rice", "1 bowl", 12),
                Ingredient("Fresh brown egg", "1 pc", 9),
                Ingredient("Minced garlic & oil", "—", 6),
                Ingredient("Soy-calamansi dip", "—", 8)
            ),
            steps = listOf(
                Step("Sear garlic", 2, "Toast minced garlic in oil until golden."),
                Step("Fry rice", 4, "Add cold rice, toss until coated and hot."),
                Step("Fry egg", 3, "Sunny-side up in the same pan."),
                Step("Plate", 1, "Serve with soy-calamansi dip.")
            )
        ),
        Recipe(
            id = 5L,
            title = "Sweet Banana Maruya",
            description = "Golden saba fritters coated in light batter and sugar.",
            flavor = Flavor.SWEET,
            price = 30, minutes = 10, kcal = 240, protein = 3,
            imageName = "maruya",
            tags = listOf("Merienda", "Sweet"),
            ingredients = listOf(
                Ingredient("Saba bananas", "2 ripe", 16),
                Ingredient("Flour batter", "—", 14)
            ),
            steps = listOf(
                Step("Mash", 2, "Mash bananas, fold into batter."),
                Step("Fry", 6, "Fry spoonfuls until golden."),
                Step("Sugar", 2, "Dust with white sugar.")
            )
        )
    )

    fun byTitle(title: String): Recipe? = all.firstOrNull { it.title == title }
}