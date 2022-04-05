package com.example.captaincook.ingredients.usecase

import androidx.lifecycle.LiveData
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.ingredients.repo.IngredientsRepository
import com.example.captaincook.recipes.repo.RecipesRepository
import java.lang.Exception
import javax.inject.Inject

interface GetAllIngredientsUseCase {
    operator fun invoke(): LiveData<List<Ingredient>>
}

class GetAllIngredientsUseCaseImpl @Inject constructor(
    val repo: IngredientsRepository
) : GetAllIngredientsUseCase {
    override fun invoke(): LiveData<List<Ingredient>> {
        try {
            return repo.getAllIngredients()
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}