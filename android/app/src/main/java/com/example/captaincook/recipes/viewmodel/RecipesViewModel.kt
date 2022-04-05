package com.example.captaincook.recipes.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captaincook.add_recipe.usecase.*
import com.example.captaincook.details.usecase.FindOneRecipeUseCase
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.ingredients.repo.IngredientsRepository
import com.example.captaincook.ingredients.usecase.GetAllIngredientsUseCase
import com.example.captaincook.recipe_ingredients.domain.Recipe_Ingredients
import com.example.captaincook.recipe_ingredients.usecase.GetAllRecipeIngredientsUseCase
import com.example.captaincook.recipe_ingredients.usecase.IngredientsListForRecipeUseCase
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.my_recipes.usecase.GetAllRecipesForUserUseCase
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipes.repo.RecipesRepository
import com.example.captaincook.recipes.usecase.CacheRecipesUseCase
import com.example.captaincook.recipes.usecase.GetRecipesListSizeUseCase
import com.example.captaincook.recipes.usecase.GetRecipesRemoteUseCase
import com.example.captaincook.recipes.usecase.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getRecipesRemoteUseCase: GetRecipesRemoteUseCase,
    private val findOneRecipeUseCase: FindOneRecipeUseCase,
    private val getIngredientsListForRecipeUseCase: IngredientsListForRecipeUseCase,
    private val getAllRecipeIngredientsUseCase: GetAllRecipeIngredientsUseCase,
    private val getAllRecipesForUserUseCase: GetAllRecipesForUserUseCase,
    private val getAllIngredientsUseCase: GetAllIngredientsUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val getRecipesListSizeUseCase: GetRecipesListSizeUseCase,

    private val repoRecipe: RecipesRepository,
    private val repoRecipeIngredients: Recipe_IngredientsRepository,
    private val repoIngredients: IngredientsRepository,
    private val addRecipeRemoteUseCase: AddRecipeRemoteUseCase,
    private val updateRecipeRemoteUseCase: UpdateRecipeRemoteUseCase,
    private val syncDataUseCase: SyncDataUseCase,

//    private val cacheRecipesUseCase: CacheRecipesUseCase,

) : ViewModel() {

//    private val _listOfRecipes: MutableState<List<Recipe>> = mutableStateOf(emptyList())
//    val listOfRecipes: State<List<Recipe>> = _listOfRecipes
//    private val _listOfRecipesForUser: MutableState<List<Recipe>> = mutableStateOf(emptyList())
//    val listOfRecipesForUser: State<List<Recipe>> = _listOfRecipesForUser

    var isNetworkAvailable: MutableLiveData<Boolean> =  MutableLiveData<Boolean>(true);
    var errorMessage: MutableState<String> = mutableStateOf("")

    lateinit var listOfRecipes: LiveData<List<Recipe>>
    lateinit var listOfRecipesForUser: LiveData<List<Recipe>>

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

//    init {
////            val recipeList = getRecipesUseCase()
////            _listOfRecipes.value = recipeList
//
//        viewModelScope.launch {
//            repoRecipe.cacheRecipes(repoRecipe.getAllRecipesRemote())
//            repoRecipeIngredients.cacheRecipeIngrediesnts(repoRecipeIngredients.getAllRecipeIngredientsRemote())
//            repoIngredients.cacheIngredients(repoIngredients.getAllIngredientsRemote())
//        }
//        listOfRecipes = getRecipesUseCase()
//        listOfRecipesForUser = getAllRecipesForUserUseCase(1)
////            val recipeListForUser = getAllRecipesForUserUseCase(1)
////            _listOfRecipesForUser.value = recipeListForUser
//
//    }
    fun load() = effect{
        if (isNetworkAvailable.value == true) {
            _isLoading.value = true
            repoRecipe.cacheRecipes(repoRecipe.getAllRecipesRemote())
            repoIngredients.cacheIngredients(repoIngredients.getAllIngredientsRemote())
            repoRecipeIngredients.cacheRecipeIngrediesnts(repoRecipeIngredients.getAllRecipeIngredientsRemote())

            listOfRecipes = getRecipesUseCase()
            listOfRecipesForUser = getAllRecipesForUserUseCase(1)
            _isLoading.value = false
        }
        else {
            _isLoading.value = true
            listOfRecipes = getRecipesUseCase()
            listOfRecipesForUser = getAllRecipesForUserUseCase(1)
            _isLoading.value = false
        }
    }
    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    fun addRecipe(recipe:Recipe,list:List<Int>){
        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {
                try {
                    addRecipeRemoteUseCase(recipe, list)
                    addRecipeUseCase(recipe, list)
                    Log.d("Debug","Recipe "+recipe.recipe_name + " added.")
                } catch (e: Exception) {
                    Log.d("Error while adding recipe: ", e.toString())
                    errorMessage.value = "Add error: " + e.message.toString()
                    Log.d("Error while adding recipe: ", errorMessage.value)
                }
            }
            else{
                try {
                    recipe.offline_operation = true
                    addRecipeUseCase(recipe, list)
                    Log.d("Debug","Recipe "+recipe.recipe_name + " added.")

                } catch (e: Exception) {
                    Log.d("Error while adding recipe: ", e.toString())
                    errorMessage.value = "Add error: " + e.message.toString()
                }
            }
        }
    }
    fun deleteRecipe(recipeID:Int){
        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {
                try {
                    repoRecipe.deleteRecipeRemote(recipeID)
                    deleteRecipeUseCase(recipeID)
                    Log.d("Debug","Recipe with ID "+ recipeID + " deleted.")
                } catch (e: Exception) {
                    Log.d("Error while deleting recipe: ", e.toString())
                    errorMessage.value = "Delete error: " + e.message.toString()
                }
            }
            else  {
                try {
                    deleteRecipeUseCase(recipeID)
                    Log.d("Debug","Recipe with ID "+recipeID+ " deleted.")
                } catch (e: Exception) {
                    Log.d("Error while deleting recipe: ", e.toString())
                    errorMessage.value = "Delete error: " + e.message.toString()
                }
            }
        }
    }
    fun updateRecipe(recipe:Recipe,newRecipe:Recipe,newList:List<Int>){

        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {
                try {
                    updateRecipeRemoteUseCase(recipe, newRecipe, newList)
                    updateRecipeUseCase(recipe, newRecipe, newList)
                    Log.d("Debug","Recipe "+recipe.recipe_name + " updated.")
                } catch (e: Exception) {
                    Log.d("Error while updating recipe: ", e.toString())
                    errorMessage.value = "Update error: " + e.message.toString()
                }
            }
            else{
                try {
//                    newRecipe.offline_operation = true
                    updateRecipeUseCase(recipe, newRecipe, newList)
                    Log.d("Debug","Recipe "+recipe.recipe_name + "updated.")

                } catch (e: Exception) {
                    Log.d("Error while updating recipe: ", e.toString())
                    errorMessage.value = "Update error: " + e.message.toString()
                }
            }
        }
    }

    fun findOneRecipe(recipeID: Int):Recipe {
        try {
            Log.d("Debug","Recipe with ID "+recipeID + " read.")
            return findOneRecipeUseCase(recipeID)
        }catch(e:Exception){
            Log.d("Error while reading recipe with ID : "+recipeID, e.toString())
            errorMessage.value = "FindOne error: " + e.message.toString()
            return Recipe(-100,-1,"","",-1,-1,"",false);
        }

//        var waitingRecipe = Recipe(0,0,"","",0,0,"",false);
//        if (isNetworkAvailable.value == true) {
//            try {
//                runBlocking {
//                    runBlocking { waitingRecipe = async {  repoRecipe.findOneRecipeRemote(recipeID)}.await() }
//                }
//                Log.d("Debug","Recipe with ID "+recipeID + "read.")
//
//                return waitingRecipe
//            } catch (e: Exception) {
//                Log.d("Error while reading recipe: ", e.toString())
//                return Recipe(-100, -1, "", "", -1, -1, "", false);
//            }
//        }
//        else{
//            try{
//                Log.d("Debug","Recipe with ID "+recipeID + "read.")
//                return findOneRecipeUseCase(recipeID)
//            }catch (e:Exception){
//                Log.d("Error while reading recipe: ", e.toString())
//                return Recipe(-100, -1, "", "", -1, -1, "", false);
//            }
//        }
    }


    fun getIngredientsListForRecipe(recipeID:Int): LiveData<List<Ingredient>> {
        Log.d("Debug","Get ingredients list for recipe called")
        return getIngredientsListForRecipeUseCase(recipeID)
    }

    fun getAllIngredients():LiveData<List<Ingredient>>{
        Log.d("Debug","Get all ingredients called")
        return getAllIngredientsUseCase()
    }

    fun syncData() {
        viewModelScope.launch {
            syncDataUseCase()

            repoRecipe.cacheRecipes(repoRecipe.getAllRecipesRemote())
            repoIngredients.cacheIngredients(repoIngredients.getAllIngredientsRemote())
            repoRecipeIngredients.cacheRecipeIngrediesnts(repoRecipeIngredients.getAllRecipeIngredientsRemote())

            listOfRecipes = getRecipesUseCase()
            listOfRecipesForUser = getAllRecipesForUserUseCase(1)

            Log.d("Debug","Network is back.Sync operation done.")
        }
    }
//    fun addRecipe(recipe:Recipe,list:List<Int>):String{
//        var error=""
//        viewModelScope.launch {
//            try {
//                var r = addRecipeUseCase(recipe, list)
//    //            val recipeListForUser = getAllRecipesForUserUseCase(1)
//            }catch(e:Exception) {
//                Log.d("Error while adding recipe: ", e.toString())
//                error = "Add error: " + e.toString()
//            }
//        }
////        _listOfRecipesForUser.value = recipeListForUser
//        return error
//    }
//    fun deleteRecipe(recipeID:Int):String{
//        var error=""
//        viewModelScope.launch {
//            try {
//                deleteRecipeUseCase(recipeID)
//            } catch (e: Exception) {
//                Log.d("Error while deleting recipe: ",e.toString())
//                error="Delete error: "+e.toString()
//            }
//        }
////        val recipeListForUser = getAllRecipesForUserUseCase(1)
////        _listOfRecipesForUser.value = recipeListForUser
//        return error
//    }
//    fun updateRecipe(recipe:Recipe,newRecipe:Recipe,newList:List<Int>):String{
//        var error=""
//        viewModelScope.launch {
//            try {
//                updateRecipeUseCase(recipe, newRecipe, newList)
//            }catch(e:Exception){
//                Log.d("Error while updating recipe: ",e.toString())
//                error="Update error: "+e.toString()
//            }
//        }
////        val recipeListForUser = getAllRecipesForUserUseCase(1)
////        _listOfRecipesForUser.value = recipeListForUser
//        return error;
//    }
//
//    fun findOneRecipe(recipeID: Int):Recipe {
//        try {
//            return findOneRecipeUseCase(recipeID)
//        }catch(e:Exception){
//            Log.d("Error: ",e.toString())
//            return Recipe(-100,-1,"","",-1,-1,"");
//        }
//    }
//
//
//
//    fun getIngredientsListForRecipe(recipeID:Int):LiveData<List<Ingredient>>{
//        return getIngredientsListForRecipeUseCase(recipeID)
//    }
//    fun getAllRecipeIngredients():LiveData<List<Recipe_Ingredients>>{
//        return getAllRecipeIngredientsUseCase()
//    }
//    //    fun getAllRecipesForUser(userID:Int):List<Recipe>{
////        return getAllRecipesForUserUseCase(userID)
////    }
//    fun getAllIngredients():LiveData<List<Ingredient>>{
//        return getAllIngredientsUseCase()
//    }
//    fun getRecipesListSize() : Int{
//        return getRecipesListSizeUseCase()
//    }

}