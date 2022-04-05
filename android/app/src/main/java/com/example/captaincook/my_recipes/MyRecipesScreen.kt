package com.example.captaincook.my_recipes

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.captaincook.R
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.viewmodel.RecipesViewModel

@Composable
fun MyRecipesScreen(
    viewModel: RecipesViewModel,
    onMyRecipesClick: () -> Unit,
    onDiscoverClick: ()->Unit,
    onRecipeClick: (recipeID: Int)->Unit,
    onAddRecipeClick: (Int) ->Unit,
    onUpdateClick: (Int) -> Unit,
) {

//    val listOfRecipes by remember {viewModel.listOfRecipesForUser}
    val listOfRecipes = viewModel.listOfRecipesForUser.observeAsState(listOf()).value
    Column {
        Header(onAddRecipeClick = onAddRecipeClick)

        LazyColumn(modifier = Modifier.height(580.dp)) {
            items(listOfRecipes) { item ->
                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                        SingleRecipeItem(
                            recipe = item, onRecipeClick = onRecipeClick,viewModel = viewModel,onUpdateClick = onUpdateClick
                     )
                    }
                }
            }
        }
        Footer(
            onMyRecipesClick = onMyRecipesClick,
            onDiscoverClick = onDiscoverClick
        )
    }

@Composable
fun Header(
    onAddRecipeClick: (Int) ->Unit,
) {
    Row( modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = R.drawable.profilepicture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
    Card(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(imageVector = Icons.Default.AddCircle,
                contentDescription = "",
                Modifier
                    .size(50.dp)
                    .clickable {
                        onAddRecipeClick(-1)
                    },
                tint = Color.Red)
        }
    }
}

@Composable
fun Footer(
    onMyRecipesClick: () -> Unit,
    onDiscoverClick: ()->Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp)
            ,
        Alignment.BottomStart,
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(63.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,verticalAlignment = Alignment.CenterVertically){

            Column(modifier = Modifier
                .clickable {
                    onDiscoverClick()
                })
            {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "",
                    Modifier.size(30.dp),
                    )

                Text("Discover",)
            }
            Column(modifier = Modifier
                .clickable {
                    onMyRecipesClick()
                }) {
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "",
                    Modifier.size(30.dp),
                    tint = Color.Red)
                Text("My Recipes",fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleRecipeItem(
    viewModel: RecipesViewModel,
    recipe: Recipe,
    onRecipeClick: (Int) -> Unit,
    onUpdateClick: (Int ) ->Unit,
) {
    Card(

        modifier = Modifier
            .padding(start = 20.dp, top = 6.dp, end = 6.dp, bottom = 6.dp)
            .width(300.dp)
            .height(200.dp)
            .clickable {
                onRecipeClick(recipe.id)
            }
        //.wrapContentHeight()
        //.wrapContentWidth()
    ) {Row(horizontalArrangement = Arrangement.Center){
        Box(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Column {
                    Image(
                        modifier = Modifier
                            .height(250.dp)
                            .width(250.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        painter = if (recipe.recipe_image_url != null) {
                            rememberImagePainter(
                                recipe.recipe_image_url
                            )
                        } else {
                            painterResource(id = R.drawable.food)
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

            }


        }
    }
        Row(verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center){
            Text(
                text = recipe.recipe_name, modifier = Modifier
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
    Column(Modifier.padding(top=15.dp)) {
        Row() {
            val openDialog = remember { mutableStateOf(false)  }
            Icon(
                imageVector = Icons.Default.Delete,//Octicons.Grabber16.GitHub
                contentDescription = "",
                Modifier
                    .size(40.dp)
                    .clickable {
                        openDialog.value = true
                    },
            )
            val context = LocalContext.current
            var repoError=""
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text ="Delete Recipe")
                    },
                    text = {
                        Text("Are you sure you want to delete this recipe?")
                    },
                    confirmButton = {
                        Button(
                            colors=ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                            onClick = {
                                openDialog.value = false;
                                viewModel.deleteRecipe(recipe.id)

                                if(viewModel.errorMessage.value!="") {
                                    Toast.makeText(
                                        context,
                                        viewModel.errorMessage.value,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    viewModel.errorMessage.value="";
                                }

                            }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        Button(
                            colors=ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("No")
                        }
                    }
                )
            }
//            if(repoError.startsWith("Delete error:"))
//                Alert(errorString = "Error occured in the database while deleting the recipe!\n", openDialog = openDialog)
        }

        Row() {
            Icon(
                imageVector = Icons.Default.Edit,//Octicons.Grabber16.GitHub
                contentDescription = "",
                Modifier
                    .size(40.dp)
                    .clickable {
                        onUpdateClick(recipe.id)
                    },
            )

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