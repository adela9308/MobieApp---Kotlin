package com.example.captaincook.my_recipes.usecase

import androidx.lifecycle.LiveData
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface GetAllRecipesForUserUseCase {
    operator fun invoke(userID:Int): LiveData<List<Recipe>>
}

class GetAllRecipesForUserUseCaseImpl @Inject constructor(
    val repo: RecipesRepository
) : GetAllRecipesForUserUseCase {
    override fun invoke(userID:Int): LiveData<List<Recipe>> {
        try {
            return repo.getAllRecipesFromUser(userID)
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}