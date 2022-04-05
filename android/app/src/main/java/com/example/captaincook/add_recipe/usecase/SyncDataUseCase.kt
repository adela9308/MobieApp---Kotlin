package com.example.captaincook.add_recipe.usecase

import androidx.compose.runtime.livedata.observeAsState
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import java.sql.SQLException
import javax.inject.Inject

interface SyncDataUseCase {
    suspend operator fun invoke()
}

class SyncDataUseCaseImpl @Inject constructor(
    val repoRecipes: RecipesRepository,
    val repoRecipeIngredients: Recipe_IngredientsRepository,
) : SyncDataUseCase {
    override suspend fun invoke() {
        try {
            for(recipe in repoRecipes.getAllRecipesToBeSync()){
                val recipeID = recipe.id
                val ingredients = repoRecipes.ingredientsIDListForRecipe(recipeID)
                recipe.offline_operation = false
                val newRecipe=repoRecipes.addRecipeRemote(recipe)
                for (x in ingredients) {
                    val elem = Recipe_Ingredients(newRecipe.id, x,100,false)
                    repoRecipeIngredients.addRecipeIngredientsRemote(elem)
                }
            }
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}
