package com.example.captaincook.add_recipe

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.captaincook.R
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.viewmodel.RecipesViewModel


@Composable
fun AddRecipeScreen(
    viewModel: RecipesViewModel,
    onSubmitClick2: () -> Unit,
    recipeID:Int,
) {
    var recipe:Recipe = Recipe(-1,-1,"","",0,0,"",false)
    if(recipeID!=-1)
        recipe = viewModel.findOneRecipe(recipeID = recipeID)
    var _recipeImage = ""
    var _recipeName = ""
    var _recipePreparation = ""
    var _recipePreparationTime = ""
    var _recipeCalories = ""
    val _selectedIngredients: ArrayList<Int> = arrayListOf()
    if(recipe.id!=-1) {
        _recipeImage=recipe.recipe_image_url.toString()
        _recipeName=recipe.recipe_name
        _recipePreparation= recipe.recipe_preparation
        _recipePreparationTime = recipe.recipe_preparation_time.toString()
        _recipeCalories =recipe.recipe_calories.toString()
        for(x in viewModel.getIngredientsListForRecipe(recipeID = recipeID).observeAsState(listOf()).value)
            _selectedIngredients.add(x.id)
    }

    var recipeImage by remember { mutableStateOf(_recipeImage)}
    var recipeName by remember { mutableStateOf(_recipeName) }
    var recipePreparation by remember { mutableStateOf(_recipePreparation) }
    var recipePreparationTime by remember { mutableStateOf(_recipePreparationTime) }
    var recipeCalories by remember { mutableStateOf(_recipeCalories) }
    var selectedIngredients = _selectedIngredients
    val listOfIngredients = viewModel.getAllIngredients().observeAsState(listOf()).value

    Column(Modifier.fillMaxHeight().verticalScroll(rememberScrollState())) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New Recipe",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
        if(recipeID!=-1){
            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()){
                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
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
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            PhotoSelector(recipeID)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextInput(iconType = Icons.Default.Create,
                type = "Name",
                value = recipeName,
                onValueChanged = { newValue -> recipeName = newValue })
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextInput(iconType = Icons.Default.Info,
                type = "Preparation",
                value = recipePreparation,
                onValueChanged = { newValue -> recipePreparation = newValue })
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextInput(iconType = Icons.Default.Timer,
                type = "Preparation Time",
                value = recipePreparationTime,
                onValueChanged = { newValue -> recipePreparationTime = newValue })
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextInput(iconType = Icons.Default.Calculate,
                type = "Calories",
                value = recipeCalories,
                onValueChanged = { newValue -> recipeCalories = newValue })
        }

        Row(){
            Text(
                text="Ingredients: ",
                modifier = Modifier
                    .padding(start = 50.dp,top=25.dp),
                fontSize = 20.sp,)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                for (ingredient in listOfIngredients) {
                    Row {
                        SimpleCheckbox(selectedIngredients,ingredient)
                        Text(
                            text = ingredient.ingrediet_name,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(start = 30.dp)

                        )

                    }
                }
            }
        }

        val openDialog = remember { mutableStateOf(false)  }
        var repoError=""
        var errorString = ""
        val context = LocalContext.current
        SubmitButton(onSubmitClick = {
            openDialog.value=false
            if(recipeCalories == "") recipeCalories="0"
            if(recipeCalories.isEmpty() || recipePreparationTime.isEmpty() || recipeName.isEmpty() ||recipePreparation.isEmpty() ||
                selectedIngredients.size==0) {
                openDialog.value = true
                errorString+="The fields cannot be null!\n"
            }

            if(recipeCalories.toString().matches("-?\\d+(\\.\\d+)?".toRegex())==false)
            {
                openDialog.value = true
                recipeCalories=""
                errorString+="The field Recipe Calories must be integer!\n"
            }
            if(recipePreparationTime.toString().matches("-?\\d+(\\.\\d+)?".toRegex())==false)
            {
                openDialog.value = true
                recipePreparationTime=""
                errorString+="The field Recipe Preparation Time must be integer!\n"
            }

            if(recipeID==-1 && openDialog.value==false){
                var id : Int =0
                val r = Recipe(
                    0,
                    1,
                    recipeName,
                    recipePreparation,
                    recipePreparationTime.toInt(),
                    recipeCalories.toInt(),
                    "https://good-food.ro/wp-content/uploads/2020/03/GoodFood-LOGO-300x283px.png",
                    false
                )
                viewModel.addRecipe(r, selectedIngredients)

                onSubmitClick2()
                if(viewModel.errorMessage.value!="") {
                    Toast.makeText(
                        context,
                        viewModel.errorMessage.value,
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.errorMessage.value="";
                }

            }
            else if(recipeID != -1  && openDialog.value==false) {
                val r = Recipe(
                    recipe.id,
                    recipe.user_id,
                    recipeName,
                    recipePreparation,
                    recipePreparationTime.toInt(),
                    recipeCalories.toInt(),
                    recipe.recipe_image_url,
                    false
                )
                viewModel.updateRecipe(recipe,r,selectedIngredients)
                onSubmitClick2()
//                Log.d("D:","@@@"+viewModel.errorMessage.value)
//                if(viewModel.errorMessage.value!="") {
//                    Toast.makeText(
//                        context,
//                        viewModel.errorMessage.value,
//                        Toast.LENGTH_LONG
//                    ).show()
//                    viewModel.errorMessage.value="";
//                }
            }

        })
        if(openDialog.value)
            Alert(errorString = "The fields cannot be null\n Fields Recipe Preparation Time and Recipe Calories must be integers!\n",openDialog = openDialog)

//        if(repoError.startsWith("Add error:"))
//                    Alert(errorString = "Error occured in the database while adding the recipe!\n",openDialog = openDialog)
//
//        if(repoError.startsWith("Update error:"))
//                    Alert(errorString = "Error occured in the database while updating the recipe!\n",openDialog = openDialog)


    }

}
@Composable
fun PhotoSelector(recipeID: Int) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    Column( ) {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            if(recipeID == -1)
                Text(text = "Pick image")
            else Text(text = "Pick new image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let {  btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop)
            }
        }

    }
}

@Composable
fun Alert(errorString:String,openDialog:MutableState<Boolean>) {
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
@Composable
fun SubmitButton(
    onSubmitClick: () -> Unit,
){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onSubmitClick,
            enabled = true,
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            border = BorderStroke(0.dp, Color.Transparent),
            modifier = Modifier
                .padding(top=15.dp)
                .size(100.dp, 40.dp)

        )
        {
            Text(text = "Submit", color = Color.Gray)
        }
    }
}

@Composable
fun SimpleCheckbox(selectedIngredients: ArrayList<Int>,ingredient: Ingredient) {
    var check=false
    if(selectedIngredients.contains(ingredient.id)) check=true
    val isChecked = remember { mutableStateOf(check) }

    Checkbox(checked = isChecked.value, onCheckedChange = {
        isChecked.value = it
        if (selectedIngredients.contains(ingredient.id)) {
            selectedIngredients.remove(ingredient.id)
        } else {
            selectedIngredients.add(ingredient.id)
        }
        for (x in selectedIngredients) {
            Log.d("debug", x.toString())
        }
    })
}


@Composable
fun TextInput(
    iconType: ImageVector,
    type: String,
    value: String,
    onValueChanged: (String) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChanged,
            Modifier
                .padding(top = 30.dp)
                .width(300.dp),
            placeholder = { Text(type) },
            leadingIcon = {
                Icon(
                    imageVector = iconType,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Black,
                cursorColor = Color.Gray,
                textColor = Color.Gray,
            ),
        )
    }
}