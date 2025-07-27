package com.example.bonappetit.view

import android.graphics.BitmapFactory
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bonappetit.R
import com.example.bonappetit.model.RecipeModel
import com.example.bonappetit.viewmodel.DetailScreenVM

@Composable
fun DetailScreen(navController: NavController, recipeId: Int, viewModel: DetailScreenVM = hiltViewModel()) {

    LaunchedEffect(key1 = recipeId) {
        viewModel.getRecipe(recipeId)
    }
    
    val recipe = viewModel.selectedRecipe

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            val image = recipe.image?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.asImageBitmap()
            }
            if (image != null){
                Image(
                    bitmap = image,
                    contentDescription = "recipe image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            } else{
                Image(
                    painter = painterResource(R.drawable.gorselekle),
                    contentDescription = "görsel ekle",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(330.dp)
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }

        item {
            Text(
                text = recipe.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
        }

        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "Malzemeler",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFF4511E))
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                )
                Spacer(Modifier.height(8.dp))

                val ingredients = recipe.ingredient
                ingredients.forEach { ingredient ->
                    Row (modifier = Modifier.fillMaxWidth()){
                        Text("•", fontSize = 48.sp,modifier = Modifier.padding(start = 20.dp,end = 8.dp))
                        Text(
                            text = ingredient,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "Yapılış",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFF4511E))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = recipe.description,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Spacer(Modifier.height(30.dp))
        }

        item {
            var showDialog by remember { mutableStateOf(false) }
            Row (horizontalArrangement = Arrangement.Center){
                Button(
                    onClick = { showDialog = true },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Sil", fontSize = 20.sp)
                }

                Button(
                    onClick = { /*update recipe*/ },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Değiştir", fontSize = 20.sp)
                }
            }

            if (showDialog){
                AlertDialog(
                    onDismissRequest = {showDialog = false},
                    title = { Text("Tarifi Sil") },
                    text = { Text("Silmek istediğinize emin misiniz ? Bu işlem geri alınamaz.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                viewModel.deleteRecipe(recipe)
                                navController.navigate("list_screen")
                            }
                        ) {
                            Text("Sil")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("İptal")
                        }
                    }
                )
            }
        }
    }

}