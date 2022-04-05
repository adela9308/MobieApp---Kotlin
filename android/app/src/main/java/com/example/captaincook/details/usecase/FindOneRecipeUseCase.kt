package com.example.captaincook.details.usecase

import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface FindOneRecipeUseCase {
    operator fun invoke(recipeID: Int): Recipe
}

class FindOneRecipeUseCaseImpl @Inject constructor(
    val repo: RecipesRepository
) : FindOneRecipeUseCase {
    override fun invoke(recipeID: Int): Recipe {
        try {
            return repo.findOneRecipe(recipeID)
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}