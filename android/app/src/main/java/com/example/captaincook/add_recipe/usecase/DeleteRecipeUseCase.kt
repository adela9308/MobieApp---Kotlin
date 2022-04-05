package com.example.captaincook.add_recipe.usecase

import android.util.Log
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface DeleteRecipeUseCase {
    operator fun invoke(recipeID : Int)
}

class DeleteRecipeUseCaseImpl @Inject constructor(
    val repoRecipes: RecipesRepository,
    val repoRecipeIngredients: Recipe_IngredientsRepository,
) : DeleteRecipeUseCase {
    override fun invoke(recipeID : Int) {
        try {
            repoRecipeIngredients.deleteRecipeIngredients(recipeID)
            repoRecipes.deleteRecipe(recipeID)
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}