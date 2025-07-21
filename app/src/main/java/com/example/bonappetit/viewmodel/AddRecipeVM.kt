package com.example.bonappetit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bonappetit.database.RecipeDatabase
import com.example.bonappetit.model.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeVM @Inject constructor(private val database: RecipeDatabase) : ViewModel() {

    private val dao = database.recipeDao()

    fun saveRecipe(recipe: RecipeModel) {
        viewModelScope.launch (Dispatchers.IO){
            dao.insertData(recipe)

        }
    }

}