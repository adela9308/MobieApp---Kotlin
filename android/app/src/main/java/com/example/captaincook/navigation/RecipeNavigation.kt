package com.example.captaincook.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.captaincook.add_recipe.AddRecipeScreen
import com.example.captaincook.details.RecipeDetailsScreen
import com.example.captaincook.my_recipes.MyRecipesScreen
import com.example.captaincook.recipes.viewmodel.DiscoverScreen
import com.example.captaincook.navigation.Screen.*
import com.example.captaincook.recipes.viewmodel.RecipesViewModel

@Composable
fun RecipeNavigation(viewModelRecipes: RecipesViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Recipes.route) {
        composable(
            route = Recipes.route
        ) {
            DiscoverScreen(
                onRecipeClick = { selectedRecipe ->
                    navController.navigate("${RecipeDetails.route}/$selectedRecipe")
                },
                viewModel=viewModelRecipes,
                onMyRecipesClick = {navController.navigate(MyRecipes.route)},
                onDiscoverClick = {navController.navigate(Recipes.route)})
        }
        composable(
            route = "${RecipeDetails.route}/{selectedRecipe}",
            arguments = listOf(navArgument("selectedRecipe") { type = NavType.IntType })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("selectedRecipe")?.let { recipe ->
                RecipeDetailsScreen(selectedRecipeID = recipe,viewModel = viewModelRecipes,
                    onMyRecipesClick = {navController.navigate(MyRecipes.route)},
                    onDiscoverClick = {navController.navigate(Recipes.route)},
                    onBackClick = {navController.popBackStack()})
            }
        }
        composable(
            route = MyRecipes.route,
        ){
            MyRecipesScreen(
                viewModel = viewModelRecipes,
                onMyRecipesClick = {navController.navigate(MyRecipes.route)},
                onDiscoverClick = {navController.navigate(Recipes.route)},
                onRecipeClick = { selectedRecipe ->
                    navController.navigate("${RecipeDetails.route}/$selectedRecipe")
                },
                onAddRecipeClick = {selectedRecipe ->navController.navigate("${AddRecipe.route}/$selectedRecipe")},
                onUpdateClick = {selectedRecipe ->navController.navigate("${AddRecipe.route}/$selectedRecipe")},
            )
        }
        composable(
            route="${AddRecipe.route}/{selectedRecipe}",
            arguments = listOf(navArgument("selectedRecipe") { type = NavType.IntType })
        ){
                navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("selectedRecipe")?.let { recipe ->
                Log.d("debug", recipe.toString())
            AddRecipeScreen(  viewModel = viewModelRecipes,onSubmitClick2 = {navController.popBackStack()},
                recipeID = recipe)
                }
        }

    }
}