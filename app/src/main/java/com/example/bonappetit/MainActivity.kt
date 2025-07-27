package com.example.bonappetit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bonappetit.ui.theme.BonAppetitTheme
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
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BonAppetitTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {if (currentRoute == "list_screen"){ AppBar(navController) }},
                    topBar = {if( currentRoute == "list_screen" ){ TopBar() }}
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){

                        NavHost(navController = navController, startDestination = "list_screen"){

                            composable("list_screen") {
                                MainScreen(navController)
                            }

                            composable("detail_screen/{recipeId}", arguments = listOf(
                                navArgument(name = "recipeId"){ type = NavType.IntType }
                            )) {
                                val id = it.arguments?.getInt("recipeId")
                                DetailScreen(navController = navController, recipeId = id ?:0)
                            }

                            composable("add_recipe"){
                                AddRecipeScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar(navController: NavController) {
    FloatingActionButton(
        onClick = {navController.navigate("add_recipe")},
        contentColor = Color.White,
        containerColor = Color(0xFFE64A19),
        shape = CircleShape
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {Text(
            text = "Bon Appetit",
            modifier = Modifier.fillMaxSize(),
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE64A19), titleContentColor = Color.White),
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
    )
}
