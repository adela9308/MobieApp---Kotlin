package com.example.captaincook.ingredients.repo

import androidx.lifecycle.LiveData
import com.example.captaincook.R
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipeDao
import com.example.captaincook.recipes.service.RecipeService
import java.sql.SQLException
import javax.inject.Inject

interface IngredientsRepository {
    fun getAllIngredients(): LiveData<List<Ingredient>>
    suspend fun getAllIngredientsRemote(): List<Ingredient>
    fun findOneIngredient(ingredientID: Int): Ingredient

    suspend fun cacheIngredients(ingredientsFromServer:List<Ingredient>)
}

class IngredientsRepositoryImpl @Inject constructor(
    private val ingredientDao: RecipeDao,
    private val service:RecipeService
) : IngredientsRepository {
//    val allIngredients: ArrayList<Ingredient> =
//        arrayListOf(
//           Ingredient(0,"Flour"),
//           Ingredient(1,"Sugar"),
//           Ingredient(2,"Milk"),
//           Ingredient(3,"Egg"),
//           Ingredient(4,"Cinnamon"),
//           Ingredient(5,"Lemon"),
//        )
    override fun getAllIngredients(): LiveData<List<Ingredient>> {
//        return allIngredients
    try {
        return ingredientDao.getAllIngredients()
    }catch(exception: SQLException){
            throw (Exception("Error while getting all ingredients"));
        }
    }
    override suspend fun getAllIngredientsRemote(): List<Ingredient>{
        return service.getAllIngredients()
    }

    override fun findOneIngredient(ingredientID: Int): Ingredient {
//        for(ingredient in allIngredients){
//            if(ingredient.id == ingredientID)  return ingredient
//        }
//        return Ingredient(-1,"")
        try {
            return ingredientDao.findOneIngredient(ingredientID)
        }
        catch(exception: SQLException){
        throw (Exception("Error while searching ingredient"));
    }
    }

    override suspend fun cacheIngredients(ingredientsFromServer:List<Ingredient>){
        try{
            ingredientDao.deleteAllIngredients()
            for( ingredient in ingredientsFromServer)
                ingredientDao.addIngredient(ingredient)
        }
        catch (e:SQLException){
            throw (java.lang.Exception("Error while caching Ingredient Data"))
        }
    }
}