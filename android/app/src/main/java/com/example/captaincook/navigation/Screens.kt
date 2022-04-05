package com.example.captaincook.navigation

sealed class Screen(val route: String) {
    object Recipes : Screen(route = "recipes")
    object RecipeDetails : Screen(route = "recipe_details")
    object MyRecipes : Screen(route = "my_recipes")
    object AddRecipe : Screen(route = "add_recipe")
}