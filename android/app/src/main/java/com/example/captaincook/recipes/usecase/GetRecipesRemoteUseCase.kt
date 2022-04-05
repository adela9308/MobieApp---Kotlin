package com.example.captaincook.recipes.usecase

import androidx.lifecycle.LiveData
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface GetRecipesRemoteUseCase {
    suspend operator fun invoke(): List<Recipe>
}

class GetRecipesRemoteUseCaseImpl @Inject constructor(
    val repo: RecipesRepository
) : GetRecipesRemoteUseCase {
    override suspend fun invoke(): List<Recipe> {
        try {
            return repo.getAllRecipesRemote()
        }catch(exception:Exception){
            throw Exception(exception)
        }
    }
}