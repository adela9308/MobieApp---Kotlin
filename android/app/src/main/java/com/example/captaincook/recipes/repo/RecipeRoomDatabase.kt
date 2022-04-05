package com.example.captaincook.recipes.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipes.domain.Recipe

@Database(entities = [Recipe::class,Ingredient::class,Recipe_Ingredients::class],version=1,exportSchema = false)
abstract class RecipeRoomDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}