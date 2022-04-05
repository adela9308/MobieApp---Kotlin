package com.example.captaincook.recipes.service

import androidx.lifecycle.LiveData
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipes.domain.Recipe
import retrofit2.http.*

interface RecipeService {
    @GET("captaincook/api/recipes/{recipeID}")
    suspend fun getRecipeByID(@Path("recipeID")recipeID:Int): Recipe

    @GET("captaincook/api/recipes/all")
    suspend fun getAllRecipes(): List<Recipe>

    @GET("captaincook/api/recipes/my_recipes")
    suspend fun getMyRecipes(): List<Recipe>

    @GET("captaincook/api/ingredients/all")
    suspend fun getAllIngredients(): List<Ingredient>

    @GET("captaincook/api/recipe_ingredients/recipe/{recipeID}")
    suspend fun getIngredientsForRecipe(@Path("recipeID") recipeID:Int): LiveData<List<Ingredient>>

    @GET("captaincook/api/recipe_ingredients/all")
    suspend fun getAllRecipeIngredients(): List<Recipe_Ingredients>

    @POST("captaincook/api/recipes")
    suspend fun addRecipe(@Body recipe:Recipe):Recipe

    @POST("captaincook/api/recipe_ingredients")
    suspend fun addRecipeIngredient(@Body recipeIngredient:Recipe_Ingredients)

    @DELETE("captaincook/api/recipe_ingredients/{recipeID}")
    suspend fun deleteRecipeIngredient(@Path("recipeID") recipeID:Int)

    @DELETE("captaincook/api/recipes/{recipeID}")
    suspend fun deleteRecipe(@Path("recipeID") recipeID:Int)

    @PUT("captaincook/api/recipes/{recipeID}")
    suspend fun updateRecipe(@Path("recipeID") recipeID:Int, @Body newRecipe:Recipe)

}