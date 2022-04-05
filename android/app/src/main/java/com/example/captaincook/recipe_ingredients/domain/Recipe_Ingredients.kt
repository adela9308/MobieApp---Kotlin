package com.example.captaincook.recipe_ingredients.domain

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "recipe_ingredients_table",primaryKeys = ["recipe_id","ingredient_id"])
data class Recipe_Ingredients(
    @ColumnInfo(name = "recipe_id") val recipe_id: Int,
    @ColumnInfo(name = "ingredient_id") val ingredient_id:Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "offline_operation") var offline_operation: Boolean
)