package com.example.captaincook.recipes.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "user_id") var user_id: Int,
    @ColumnInfo(name = "recipe_name") var recipe_name: String,
    @ColumnInfo(name = "recipe_preparation") var recipe_preparation:String,
    @ColumnInfo(name = "recipe_preparation_time") var recipe_preparation_time: Int,
    @ColumnInfo(name = "recipe_calories") var recipe_calories: Int,
//    var imagineUrl: Int
    @ColumnInfo(name = "recipe_image_url") var recipe_image_url: String,
    @ColumnInfo(name = "offline_operation") var offline_operation: Boolean
    //val imagine:File
)