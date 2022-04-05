package com.example.captaincook.recipe_ingredients.repo

import androidx.lifecycle.LiveData
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.repo.RecipeDao
import com.example.captaincook.recipes.service.RecipeService
import java.sql.SQLException
import javax.inject.Inject

interface Recipe_IngredientsRepository {
    fun getAllRecipeIngredients():LiveData<List<Recipe_Ingredients>>
    suspend fun getAllRecipeIngredientsRemote():List<Recipe_Ingredients>
    fun addRecipeIngredients(x: Recipe_Ingredients)
    suspend fun addRecipeIngredientsRemote(x: Recipe_Ingredients)
    fun deleteRecipeIngredients(recipeID: Int)
    suspend fun deleteRecipeIngredientsRemote(recipeID: Int)
    fun updateRecipeIngredients(recipe:Recipe,newList:List<Recipe_Ingredients>)
    suspend fun updateRecipeIngredientsRemote(recipe:Recipe,newList:List<Recipe_Ingredients>)
    fun ingredientsListForRecipe(recipeID: Int): LiveData<List<Ingredient>>
    suspend fun ingredientsListForRecipeRemote(recipeID: Int): LiveData<List<Ingredient>>

    suspend fun cacheRecipeIngrediesnts(recipeIngredientsFromServer:List<Recipe_Ingredients>)
}

class Recipe_IngredientsRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val service: RecipeService

) : Recipe_IngredientsRepository {
    //val repoIngredients:IngredientsRepository,
//    var allRecipeIngredients: ArrayList<Recipe_Ingredients> =
//        arrayListOf(
//            Recipe_Ingredients(3,0,100),
//            Recipe_Ingredients(3,2,80),
//            Recipe_Ingredients(3,5,20),
//            Recipe_Ingredients(4,1,100),
//            Recipe_Ingredients(4,3,10),
//            Recipe_Ingredients(4,4,10),
//        )

    override fun getAllRecipeIngredients(): LiveData<List<Recipe_Ingredients>> {
        try{
            return recipeDao.getAllRecipeIngredients();
        }
        catch(exception: SQLException){
            throw (Exception("Error while getting all recipe_ingredients"));
        }
    }

    override suspend fun getAllRecipeIngredientsRemote():List<Recipe_Ingredients>{
        try{
            return service.getAllRecipeIngredients();
        }
        catch(exception: SQLException){
            throw (Exception("Error while getting all recipe_ingredients"));
        }
    }
    override fun addRecipeIngredients(x: Recipe_Ingredients) {
        try {
            recipeDao.addRecipeIngredient(x);
        }
        catch(exception: SQLException){
            throw (Exception("Error while adding recipe_ingredients"));
    }
//        allRecipeIngredients.add(x)
    }

    override suspend fun addRecipeIngredientsRemote(x: Recipe_Ingredients) {
        try {
            service.addRecipeIngredient(x)
        } catch (exception: SQLException) {
            throw (Exception("Error while adding recipe_ingredients"));
        }
    }
    override fun deleteRecipeIngredients(recipeID: Int) {
//        var index_list: ArrayList<Int> = arrayListOf()
//        var i=0;
//        for(x in allRecipeIngredients){
//            if(x.recipeID == recipeID)
//                index_list.add(i);
//            i++;
//        }
//        Collections.sort(index_list, Collections.reverseOrder());
//        for(ind in index_list)
//            allRecipeIngredients.removeAt(ind)
        try {
            recipeDao.deleteRecipeIngredientsByRecipeID(recipeID);
        }
        catch(exception: SQLException){
            throw (Exception("Error while deleting recipe_ingredients"));
        }
    }
    override suspend fun deleteRecipeIngredientsRemote(recipeID: Int) {
        try {
            service.deleteRecipeIngredient(recipeID)
        }
        catch(exception: SQLException){
            throw (Exception("Error while deleting recipe_ingredients"));
        }
    }
    override fun updateRecipeIngredients(recipe: Recipe, newList: List<Recipe_Ingredients>) {
//        deleteRecipeIngredients(recipe.id)
//        for(x in newList)
//            addRecipeIngredients(x)
        try {
            recipeDao.deleteRecipeIngredientsByRecipeID(recipe.id);
            for (x in newList)
                recipeDao.addRecipeIngredient(x)
        }catch(exception: SQLException){
            throw (Exception("Error while updating recipe_ingredients"));
        }

    }
    override suspend fun updateRecipeIngredientsRemote(recipe: Recipe, newList: List<Recipe_Ingredients>) {
        try {
            service.deleteRecipeIngredient(recipe.id)
            for (x in newList)
                service.addRecipeIngredient(x)
        }catch(exception: SQLException){
            throw (Exception("Error while updating recipe_ingredients"));
        }
    }

    override fun ingredientsListForRecipe(recipeID: Int): LiveData<List<Ingredient>> {
        try {
            return recipeDao.ingredientsListForRecipe(recipeID)
        }
        catch(exception: SQLException){
            throw (Exception("Error while getting ingredients list for recipe"));
        }
    }
    override suspend fun ingredientsListForRecipeRemote(recipeID: Int): LiveData<List<Ingredient>> {
        try {
            return service.getIngredientsForRecipe(recipeID)
        }
        catch(exception: SQLException){
            throw (Exception("Error while getting ingredients list for recipe"));
        }

    }

    override suspend fun cacheRecipeIngrediesnts(recipeIngredientsFromServer:List<Recipe_Ingredients>){
        try{
            recipeDao.deleteAllRecipeIngredients()
            for( recipeIngredient in recipeIngredientsFromServer)
                recipeDao.addRecipeIngredient(recipeIngredient)
        }
        catch (e:SQLException){
            throw (java.lang.Exception("Error while caching Recipe Ingredients Data"))
        }
    }
}