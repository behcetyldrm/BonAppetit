package com.example.bonappetit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bonappetit.database.RecipeDatabase
import com.example.bonappetit.model.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailScreenVM @Inject constructor(
    private val database: RecipeDatabase
): ViewModel() {

    private val dao = database.recipeDao()
    var selectedRecipe by mutableStateOf(RecipeModel(
        name = "", ingredient = listOf() , description = "", image = ByteArray(0))
    )

    fun getRecipe(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedRecipe = dao.getSelectedData(id)
        }
    }

    fun deleteRecipe(recipe: RecipeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteData(recipe)
        }
    }

    fun updateRecipe(recipe: RecipeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateData(recipe)
        }
    }
}