package com.example.captaincook.recipes.usecase

import androidx.lifecycle.LiveData
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface GetRecipesUseCase {
    operator fun invoke(): LiveData<List<Recipe>>
}

class GetRecipesUseCaseImpl @Inject constructor(
    val repo: RecipesRepository
) : GetRecipesUseCase {
    override fun invoke(): LiveData<List<Recipe>> {
        try {
            return repo.getAllRecipes()
        }catch(exception:Exception){
            throw Exception(exception)
        }
    }
}