package com.example.captaincook.recipes.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.captaincook.recipes.domain.Recipe
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DiscoverScreen(
    viewModel: RecipesViewModel,
    onMyRecipesClick : () -> Unit,
    onDiscoverClick:()->Unit,
    onRecipeClick: (Int) -> Unit
) {
//    val listOfRecipes by remember { viewModel.listOfRecipes }
    val listOfRecipes = viewModel.listOfRecipes.observeAsState(listOf()).value
    Column {

        Header()
        LazyColumn(modifier = Modifier.height(580.dp)) {
            items(listOfRecipes.windowed(2, 2, true)) { sublist ->
                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    sublist.forEach { item ->
                        SingleRecipeItem(recipe = item, onRecipeClick = onRecipeClick)
                    }
                }
            }
        }
        Footer(onMyRecipesClick = onMyRecipesClick,onDiscoverClick = onDiscoverClick)
    }
}

@Composable
fun Header(
) {
    Card(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                Modifier.padding(end = 13.dp),
                placeholder = { Text("Search...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor =  Color.Black,
                    cursorColor = Color.Gray,
                    textColor = Color.Gray,
                ),
            )
        }
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
            .height(400.dp),
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
            Modifier.size(30.dp),
            tint = Color.Blue)

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





@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleRecipeItem(
    recipe: Recipe,
    onRecipeClick: (Int) -> Unit
) {
        Card(

            modifier = Modifier
                .padding(start = 20.dp, top = 6.dp, end = 6.dp, bottom = 6.dp)
                .width(170.dp)
                .height(170.dp)
                .clickable {
                    onRecipeClick(recipe.id)
                }
            //.wrapContentHeight()
            //.wrapContentWidth()
        ){
            Box (
                modifier = Modifier.padding(8.dp)) {
//                Image(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .width(100.dp)
//                        .align(Alignment.TopCenter)
//                        .clip(RoundedCornerShape(10.dp)),
//                    painter = if (recipe.imagineUrl != null) {
//                        rememberImagePainter(
//                            recipe.imagineUrl
//                        )
//                    } else {
//                        painterResource(id = R.drawable.food)
//                    },
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop
//                )
                Image(
                    painter = rememberImagePainter(recipe.recipe_image_url),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .align(Alignment.TopCenter)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(text = recipe.recipe_name, modifier = Modifier
                    .align(Alignment.BottomCenter),
                    textAlign = TextAlign.Center)
            }
        }
    }
