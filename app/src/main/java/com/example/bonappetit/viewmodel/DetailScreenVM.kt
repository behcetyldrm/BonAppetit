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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailScreenVM @Inject constructor(
    private val database: RecipeDatabase
): ViewModel() {

    private val dao = database.recipeDao()
    var selectedRecipe by mutableStateOf(RecipeModel(
        name = "", ingredient = listOf() , description = "", image = ByteArray(0))
    )

    fun getRecipe(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val recipe = dao.getSelectedData(id)
                selectedRecipe = recipe
            }
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