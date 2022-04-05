package com.example.captaincook.recipe_ingredients.usecase

import androidx.lifecycle.LiveData
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject


interface GetAllRecipeIngredientsUseCase {
    operator fun invoke(): LiveData<List<Recipe_Ingredients>>
}
class GetAllRecipeIngredientsUseCaseImpl @Inject constructor(
    val repoRecipeIngredients: Recipe_IngredientsRepository,
) : GetAllRecipeIngredientsUseCase {
    override fun invoke(): LiveData<List<Recipe_Ingredients>> {
        try {
            return repoRecipeIngredients.getAllRecipeIngredients()
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}