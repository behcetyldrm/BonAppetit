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
class MainScreenVM @Inject constructor(private val database: RecipeDatabase): ViewModel() {

    var recipeList by mutableStateOf<List<RecipeModel>>(listOf())
    private val dao = database.recipeDao()

    init {
        getRecipeList()
    }

    fun getRecipeList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                recipeList = dao.getAllData()
            }
        }
    }
}