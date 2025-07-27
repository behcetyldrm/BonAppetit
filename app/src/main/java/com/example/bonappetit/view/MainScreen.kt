package com.example.bonappetit.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bonappetit.R
import com.example.bonappetit.model.RecipeModel
import com.example.bonappetit.viewmodel.MainScreenVM

@Composable
fun MainScreen(navController: NavController, viewmodel: MainScreenVM = hiltViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewmodel.getRecipeList()
    }
    val recipeList = viewmodel.recipeList
    RecipeListView(navController, recipeList)
}

@Composable
fun RecipeListView(navController: NavController, recipeList: List<RecipeModel>) {

    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        items(recipeList){ recipe ->
            RecipeCard(navController, recipe)
        }
    }
}

@Composable
fun RecipeCard(navController: NavController, recipe: RecipeModel?) {

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp, pressedElevation = 6.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 20.dp).clickable{
            navController.navigate("detail_screen/${recipe?.id}")
        }
    ) {

        Box (
            modifier = Modifier.fillMaxSize().height(200.dp)
        ){
            val image = recipe?.image?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.asImageBitmap()
            }

            if(image != null){
                Image(
                    bitmap = image,
                    contentDescription = "recipe image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.gorselekle),
                    contentDescription = "recipe image",
                    contentScale = ContentScale.Crop
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 250f
                    ))
            )

            Box(modifier = Modifier.fillMaxSize().padding(12.dp), contentAlignment = Alignment.BottomStart){
                Text(
                    text = recipe?.name ?:"",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}