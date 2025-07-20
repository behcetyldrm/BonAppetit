package com.example.bonappetit.util

sealed class Screen(val route: String) {

    object MainScreenRoute : Screen("main_screen")
    object DetailScreenRoute: Screen("detail_screen/{recipeId}")
    object AddRecipeScreenRoute: Screen("add_recipe_screen")
}