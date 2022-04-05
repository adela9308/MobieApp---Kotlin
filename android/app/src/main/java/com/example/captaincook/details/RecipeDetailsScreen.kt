package com.example.captaincook.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.captaincook.recipes.viewmodel.RecipesViewModel

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipesViewModel,
    selectedRecipeID: Int,
    onMyRecipesClick : () -> Unit,
    onDiscoverClick:()->Unit,
    onBackClick:() -> Unit,
) {

    val openDialog = remember { mutableStateOf(false)}
    val recipe = viewModel.findOneRecipe( selectedRecipeID )
    if(recipe.id==-100)
        com.example.captaincook.add_recipe.Alert(
            errorString = "Error occured in the database while searching for the recipe!\n", openDialog = openDialog)
    val listOfIngredients = viewModel.getIngredientsListForRecipe(recipe.id).observeAsState(listOf()).value

    Column {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
//            Icon(imageVector = Icons.Default.ArrowBack,
//                contentDescription = "",
//                Modifier
//                    .size(30.dp)
//                    .clickable {
//                        onBackClick()
//                    })
            Text(
                text = recipe.recipe_name,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(bottom = 25.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
        Row( modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center
        ) {
//            Image(
//                modifier = Modifier
//                    .height(200.dp)
//                    .width(300.dp)
//                    .padding(bottom = 20.dp)
//                    .clip(RoundedCornerShape(10.dp)),
//                painter = if (recipe.imagineUrl != -1) {
//                    rememberImagePainter(
//                        recipe.imagineUrl
//                    )
//                } else {
//                    painterResource(id = R.drawable.food)
//                },
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                alignment = Alignment.Center
//            )
            Image(
                painter = rememberImagePainter(recipe.recipe_image_url),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(300.dp)
                    .padding(bottom = 20.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        }
        Row(){
            Text(text = "Ingredients",
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start=10.dp,bottom = 10.dp),
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
        Row(
        ){
            Column(){
                for(ingredient in listOfIngredients){
                    Text(text=ingredient.ingrediet_name,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start=10.dp))

                }
            }
        }
        Row(){
            Text(text = "Preparation",
                fontSize = 25.sp,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .padding(start=10.dp,bottom = 10.dp)
            )
        }
        Row(){
            Text(text = recipe.recipe_preparation,
                 fontSize = 20.sp,
                 modifier = Modifier
                    .padding(start=10.dp,bottom = 10.dp))
        }
        Row(){
            Text(text = "Preparation Time (min)",
                fontSize = 25.sp,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .padding(start=10.dp,bottom = 10.dp)
            )
        }
        Row(){
            Text(text = recipe.recipe_preparation_time.toString()+" min",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start=10.dp,bottom = 10.dp))
        }

        Row(){
            Text(text = "Calories",
                fontSize = 25.sp,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .padding(start=10.dp,bottom = 10.dp)
            )
        }
        Row(){
            Text(text = recipe.recipe_calories.toString() +" kcal",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start=10.dp,bottom = 10.dp))
        }
        Footer(onMyRecipesClick = onMyRecipesClick,onDiscoverClick = onDiscoverClick)
    }



}


@Composable
fun Footer(
    onMyRecipesClick: () -> Unit,
    onDiscoverClick:()->Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp)
            .padding(top=20.dp),

        Alignment.BottomStart
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,verticalAlignment = Alignment.CenterVertically){

            Column(modifier = Modifier
                .clickable {
                    onDiscoverClick()
                })
            {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "",
                    Modifier.size(30.dp),)

                Text("Discover",fontWeight = FontWeight.ExtraBold)
            }
            Column(modifier = Modifier
                .clickable {
                    onMyRecipesClick()
                }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "",
                    Modifier.size(30.dp),)
                Text("My Recipes")
            }
        }
    }
}


@Composable
fun Alert(errorString:String,openDialog: MutableState<Boolean>) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Error")
            },
            text = {
                Log.d("debug", errorString)
                Text(text = errorString)
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    onClick = {
                        openDialog.value = false;
                    }) {
                    Text("Ok")
                }
            },
        )
    }
}