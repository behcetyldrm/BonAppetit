package com.example.bonappetit.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bonappetit.R
import com.example.bonappetit.model.RecipeModel
import com.example.bonappetit.viewmodel.AddRecipeVM
import java.io.ByteArrayOutputStream
import java.lang.Exception

@SuppressLint("Recycle")
@Composable
fun AddRecipeScreen(navController: NavController, viewModel: AddRecipeVM = hiltViewModel()) {

    var recipeName by remember { mutableStateOf("") }
    val recipeIngredients = remember { mutableStateListOf<String>() }
    var recipeDescription by remember { mutableStateOf("") }
    var recipeImage by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        item { ImagePicker(selectedImage = { recipeImage = it }) }

        item {
            OutlinedTextField(
                value = recipeName,
                onValueChange = { recipeName = it },
                singleLine = true,
                placeholder = { Text("Yemek Adı") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp),
                shape = CircleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFFE0B2),
                    focusedContainerColor = Color(0xFFFFCC80),
                    unfocusedBorderColor = Color(0xFFE64A19),
                    focusedBorderColor =  Color(0xFFE65100)
                )
            )
            Spacer(Modifier.height(10.dp))
        }

        items(recipeIngredients.size) { index ->
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("•", fontSize = 48.sp,modifier = Modifier.padding(end = 8.dp))
                TextField(
                    value = recipeIngredients[index],
                    onValueChange = { recipeIngredients[index] = it },
                    singleLine = true,
                    placeholder = {Text(text = "${index + 1}. Malzeme")},
                    modifier = Modifier.padding(vertical = 2.dp).width(340.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                IconButton(
                    onClick = { recipeIngredients.removeAt(index) }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete ingredients")
                }
            }
        }

        item {
            Button(
                onClick = {recipeIngredients.add("")},
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF6C00),
                    contentColor = Color.White
                )
            ) {
                Text("Malzeme ekle")
            }
        }

        item {
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = recipeDescription,
                onValueChange = { recipeDescription = it },
                singleLine = false,
                placeholder = { Text("Yemeğin Yapılışı") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp),
                shape = RoundedCornerShape(30.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFFE0B2),
                    focusedContainerColor = Color(0xFFFFCC80),
                    unfocusedBorderColor = Color(0xFFE64A19),
                    focusedBorderColor =  Color(0xFFE65100)
                )
            )
            Spacer(Modifier.height(10.dp))
        }

        item {
            Button(
                onClick = {
                    try{
                        val imageByteArray = recipeImage?.let {
                            val inputStream =context.contentResolver.openInputStream(it)
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                            byteArrayOutputStream.toByteArray()
                        } ?: ByteArray(0)

                        val recipe = RecipeModel(
                            name = recipeName, 
                            ingredient = recipeIngredients.toList(), 
                            description = recipeDescription, 
                            image = imageByteArray
                        )
                        viewModel.saveRecipe(recipe)
                        navController.navigate("list_screen")
                    } catch (e: Exception){
                        Toast.makeText(context, "Görsel yükleme hatası", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD84315),
                    contentColor = Color.White
                ),
                modifier = Modifier.width(180.dp)
            ) {
                Text("Kaydet", fontSize = 20.sp, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun ImagePicker(selectedImage: (Uri?) -> Unit) {

    var image by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        image = it
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it){
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied.", Toast.LENGTH_SHORT).show()
        }
    }

    image?.let { uri ->
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = "recipe image",
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable { galleryLauncher.launch("image/*") },
            contentScale = ContentScale.Crop
        )
        selectedImage(uri)
    } ?: Image(
        painter = painterResource(R.drawable.gorselekle),
        contentDescription = "görsel ekle",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    galleryLauncher.launch("image/*")
                } else {
                    permissionLauncher.launch(permission)
                }
            }
        )
}

