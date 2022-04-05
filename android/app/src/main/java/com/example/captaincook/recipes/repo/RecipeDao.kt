package com.example.captaincook.recipes.repo
import androidx.lifecycle.LiveData;
import androidx.room.*
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipes.domain.Recipe

@Dao
interface RecipeDao {

    //recipe
    @Query("SELECT * FROM recipes_table")
    fun getAllRecipes():LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes_table WHERE id=:recipeID")
    fun findOneRecipe(recipeID:Int):Recipe;

    @Query("SELECT * FROM recipes_table WHERE user_id=:userID")
    fun getAllRecipesFromUser(userID:Int):LiveData<List<Recipe>>;

    @Query("DELETE FROM recipes_table WHERE id=:recipeID")
    fun deleteRecipe(recipeID:Int);

    @Query("DELETE FROM recipes_table")
    fun deleteAllRecipes();

    @Query("SELECT * FROM recipes_table ORDER BY id DESC LIMIT 1")
    fun getLastRecipe():Recipe;

    @Query("SELECT * FROM recipes_table where offline_operation = 1")
    fun getAllOfflineAddedRecipes():List<Recipe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRecipe(recipe:Recipe)

    @Update
    fun updateRecipe(newRecipe:Recipe)

    //ingredients
    @Query("SELECT * from ingredients_table")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredients_table WHERE id=:ingredientID")
    fun findOneIngredient(ingredientID: Int): Ingredient;

    @Query("DELETE FROM ingredients_table WHERE id=:ingredientID")
    fun deleteIngredient(ingredientID: Int);

    @Query("DELETE FROM ingredients_table")
    fun deleteAllIngredients();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIngredient(ingredient: Ingredient)

    @Update
    fun updateIngredient(newIngredient: Ingredient)

    //recipe_ingredients\

    @Query("SELECT * from recipe_ingredients_table")
    fun getAllRecipeIngredients(): LiveData<List<Recipe_Ingredients>>


    @Query("DELETE FROM recipe_ingredients_table WHERE recipe_id=:recipeID")
    fun deleteRecipeIngredientsByRecipeID(recipeID:Int);

    @Query("DELETE FROM recipe_ingredients_table")
    fun deleteAllRecipeIngredients();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRecipeIngredient(recipe_ingredient: Recipe_Ingredients)

    @Query("SELECT * FROM ingredients_table INNER JOIN recipe_ingredients_table ON (ingredients_table.id = recipe_ingredients_table.ingredient_id and recipe_ingredients_table.recipe_id=:recipeID)")
    fun ingredientsListForRecipe(recipeID: Int): LiveData<List<Ingredient>>

    @Query("SELECT id FROM ingredients_table INNER JOIN recipe_ingredients_table ON (ingredients_table.id = recipe_ingredients_table.ingredient_id and recipe_ingredients_table.recipe_id=:recipeID)")
    fun ingredientsIDListForRecipe(recipeID: Int): List<Int>


}