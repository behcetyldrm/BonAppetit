package com.example.bonappetit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bonappetit.model.RecipeModel
import com.example.bonappetit.util.Converters

@Database(entities = [RecipeModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao
}