package com.example.captaincook.ingredients.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients_table")
data class Ingredient (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "ingrediet_name") val ingrediet_name: String,
    @ColumnInfo(name = "offline_operation") var offline_operation: Boolean
)
