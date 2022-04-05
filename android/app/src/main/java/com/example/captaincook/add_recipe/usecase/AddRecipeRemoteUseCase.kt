package com.example.captaincook.add_recipe.usecase


import android.util.Log
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface AddRecipeRemoteUseCase {
    suspend operator fun invoke(recipe : Recipe, ingredients: List<Int>): Recipe
}

class AddRecipeRemoteUseCaseImpl @Inject constructor(
    val repoRecipes: RecipesRepository,
    val repoRecipeIngredients: Recipe_IngredientsRepository,
) : AddRecipeRemoteUseCase {
    override suspend fun invoke(recipe : Recipe, ingredients: List<Int>): Recipe {
        try {
            val r=repoRecipes.addRecipeRemote(recipe)
            for (x in ingredients) {
                val elem = Recipe_Ingredients(r.id, x,100,false)
                repoRecipeIngredients.addRecipeIngredientsRemote(elem)
            }

            return r
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}