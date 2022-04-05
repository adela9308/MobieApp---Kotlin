package com.example.captaincook.add_recipe.usecase

import android.util.Log
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface UpdateRecipeRemoteUseCase {
    suspend operator fun invoke(recipe : Recipe, newRecipe:Recipe, newIngredientList:List<Int>)
}

class UpdateRecipeRemoteUseCaseImpl @Inject constructor(
    val repoRecipes: RecipesRepository,
    val repoRecipeIngredients: Recipe_IngredientsRepository,
) : UpdateRecipeRemoteUseCase {
    override suspend fun invoke(recipe: Recipe, newRecipe: Recipe, newIngredientList:List<Int>) {
        try {
            val list: ArrayList<Recipe_Ingredients> = arrayListOf()
            repoRecipes.updateRecipeRemote(recipe, newRecipe)
            for (x in newIngredientList) {
                val elem = Recipe_Ingredients(recipe.id, x, 100,false)
                list.add(elem)
            }
            repoRecipeIngredients.updateRecipeIngredientsRemote(newRecipe, list)
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}