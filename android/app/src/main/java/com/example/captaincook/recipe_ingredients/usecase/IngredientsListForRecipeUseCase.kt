package com.example.captaincook.recipe_ingredients.usecase

import androidx.lifecycle.LiveData
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import java.lang.Exception
import javax.inject.Inject


interface IngredientsListForRecipeUseCase {
    operator fun invoke(recipeID: Int): LiveData<List<Ingredient>>
}

class IngredientsListForRecipeUseCaseImpl @Inject constructor(
    val repoRecipeIngredients: Recipe_IngredientsRepository,
//    val repoIngredients: IngredientsRepository
) : IngredientsListForRecipeUseCase {
    override fun invoke(recipeID: Int): LiveData<List<Ingredient>> {
//        val list:ArrayList<Ingredient> = arrayListOf()
//        for(x in repoRecipeIngredients.getAllRecipeIngredients()){
//            Log.d("debug",recipeID.toString())
//            if(x.recipeID == recipeID){
//                list.add(repoIngredients.findOneIngredient(x.ingredientID))
//            }
//        }
//        return list;
        try {
            return repoRecipeIngredients.ingredientsListForRecipe(recipeID);
        }catch(exception: Exception){
            throw Exception(exception)
        }
    }
}