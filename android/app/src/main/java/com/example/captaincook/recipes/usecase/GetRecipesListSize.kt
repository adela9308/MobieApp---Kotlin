package com.example.captaincook.recipes.usecase

import android.graphics.PointF.length
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipesRepository
import javax.inject.Inject


interface GetRecipesListSizeUseCase {
    operator fun invoke(): Int
}

class GetRecipesListSizeUseCaseImpl @Inject constructor(
    val repo: RecipesRepository
) : GetRecipesListSizeUseCase {
    override fun invoke(): Int {
//        return repo.getRecipesListSize()
        return 0;
    }
}