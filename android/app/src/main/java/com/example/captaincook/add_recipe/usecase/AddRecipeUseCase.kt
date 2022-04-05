package com.example.captaincook.add_recipe.usecase

import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface AddRecipeUseCase {
    operator fun invoke(recipe : Recipe,ingredients: List<Int>): Recipe
}

class AddRecipeUseCaseImpl @Inject constructor(
    val repoRecipes: RecipesRepository,
    val repoRecipeIngredients: Recipe_IngredientsRepository,
) : AddRecipeUseCase {
    override fun invoke(recipe : Recipe,ingredients: List<Int>): Recipe {
        try {
            repoRecipes.addRecipe(recipe)
            for (x in ingredients) {
                val elem = Recipe_Ingredients(repoRecipes.getLastRecipe().id, x, 100,false)
                repoRecipeIngredients.addRecipeIngredients(elem)
            }

            return recipe
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}