package com.example.bonappetit.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bonappetit.model.RecipeModel

@Dao
interface RecipeDao {

    @Query("SELECT * FROM RecipeModel")
    fun getAllData() : List<RecipeModel>

    @Query("SELECT * FROM RecipeModel WHERE id = :recipeId")
    fun getSelectedData(recipeId: Int) : RecipeModel

    @Insert
    suspend fun insertData(recipe: RecipeModel)

    @Update
    suspend fun updateData(recipe: RecipeModel)

    @Delete
    suspend fun deleteData(recipe: RecipeModel)
}