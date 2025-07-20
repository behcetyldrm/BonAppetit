package com.example.bonappetit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bonappetit.ui.theme.BonAppetitTheme
import com.example.bonappetit.util.Screen
import com.example.bonappetit.view.AddRecipeScreen
import com.example.bonappetit.view.DetailScreen
import com.example.bonappetit.view.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            BonAppetitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){

                        NavHost(navController = navController, startDestination = Screen.MainScreenRoute.route){

                            composable(Screen.MainScreenRoute.route) {
                                MainScreen(navController)
                            }

                            composable(Screen.DetailScreenRoute.route, arguments = listOf(
                                navArgument(name = "recipeId"){ type = NavType.IntType }
                            )) {
                                val id = it.arguments?.getInt("recipeId")
                                DetailScreen(navController = navController, recipeId = id ?:0)
                            }

                            composable(Screen.AddRecipeScreenRoute.route){
                                AddRecipeScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
