package com.example.captaincook.recipes.usecase

import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import javax.inject.Inject

interface CacheRecipesUseCase {
    suspend operator fun invoke()
}

class CacheRecipesUseCaseImpl @Inject constructor(
    val repo: RecipesRepository
) : CacheRecipesUseCase {
    override suspend fun invoke(){
        repo.cacheRecipes(repo.getAllRecipesRemote())
    }
}