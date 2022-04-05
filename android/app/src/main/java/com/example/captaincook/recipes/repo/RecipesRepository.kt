package com.example.captaincook.recipes.repo

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import com.example.captaincook.R
import com.example.captaincook.ingredients.domain.Ingredient
import com.example.captaincook.recipes.domain.Recipe
import com.example.captaincook.recipes.service.RecipeService
import com.example.captaincook.recipes.service.RecipesService
import java.sql.SQLException
import javax.inject.Inject

interface RecipesRepository {
    fun getAllRecipes(): LiveData<List<Recipe>>
    suspend fun getAllRecipesRemote(): List<Recipe>
    fun findOneRecipe(recipeID: Int):Recipe
    suspend fun findOneRecipeRemote(recipeID: Int):Recipe
    fun getAllRecipesFromUser(userID:Int):LiveData<List<Recipe>>
    suspend fun getAllRecipesFromUserRemote(userID:Int):List<Recipe>
    fun addRecipe(recipe:Recipe)
    suspend fun addRecipeRemote(recipe:Recipe):Recipe
    fun deleteRecipe(recipeID:Int)
    suspend fun deleteRecipeRemote(recipeID:Int)
    fun updateRecipe(recipe:Recipe,newRecipe:Recipe)
    suspend fun updateRecipeRemote(recipe:Recipe,newRecipe:Recipe)
    fun getLastRecipe():Recipe

    suspend fun cacheRecipes(recipesFromServer:List<Recipe>)
    fun getAllRecipesToBeSync():List<Recipe>
    fun ingredientsIDListForRecipe(recipeID:Int): List<Int>
}

class RecipesRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val service: RecipeService

) : RecipesRepository {
//    val budinca="https://www.gustoase.net/wp-content/uploads/2019/09/20.jpg"
//    val placinta = "https://dulciurifeldefel.ro/wp-content/uploads/2016/10/placinta-impletita-cu-mere-si-stafide-1.jpg"
//    val macaroane = "https://www.e-retete.ro/files/recipes/macaroane-cu-branza.jpg"
//    val allRecipes: ArrayList<Recipe> =
//        arrayListOf(
//            Recipe(0,0,"Budinca cu ciocolata","Mod de preparare budina cu ciocolata",20,300, budinca),
//            Recipe(1,0,"Placinta cu mere","Mod de preparare placinta cu mere",60,1200,placinta),
//            Recipe(2,0,"Macaroane cu branza","Mod de preparare macaroane cu branza",30,340,macaroane),
//            Recipe(3,1,"*Budinca cu ciocolata","Mod de preparare budina cu ciocolata",20,300, budinca),
//            Recipe(4,1,"*Placinta cu mere","Mod de preparare placinta cu mere",60,1200,placinta),
//            Recipe(5,2,"Macaroane cu branza","Mod de preparare macaroane cu branza",30,340,macaroane),
//            Recipe(6,2,"Budinca cu ciocolata","Mod de preparare budina cu ciocolata",20,300, budinca),
//            Recipe(7,2,"Placinta cu mere","Mod de preparare placinta cu mere",60,1200,placinta),
//            Recipe(8,3,"Macaroane cu branza","Mod de preparare macaroane cu branza",30,340,macaroane)
//        )
    override fun getAllRecipes(): LiveData<List<Recipe>> {
//        return allRecipes
        return recipeDao.getAllRecipes()
    }

    override suspend fun getAllRecipesRemote(): List<Recipe> {
        return service.getAllRecipes()
    }

    override fun findOneRecipe(recipeID: Int): Recipe {
//        for(recipe in allRecipes){
//            if(recipe.id == recipeID)  return recipe
//        }
//        return Recipe(-1,-1,"","",0 ,0 ,"")
        try {
            return recipeDao.findOneRecipe(recipeID)
        }
        catch(exception:SQLException){
            throw (Exception("Error while searching recipe"));
        }
    }

    override suspend fun findOneRecipeRemote(recipeID: Int):Recipe{
        try {
            return service.getRecipeByID(recipeID)
        }
        catch(exception:SQLException){
            throw (Exception("Error while searching recipe"));
        }
    }
    override fun getAllRecipesFromUser(userID: Int): LiveData<List<Recipe>> {
//        val list:ArrayList<Recipe> = arrayListOf()
//        for(recipe in allRecipes){
//            if(recipe.idUser == userID)  list.add(recipe)
//        }
//        return list;
        try{
        return recipeDao.getAllRecipesFromUser(userID)
        }
        catch(exception:SQLException){
            throw (Exception("Error while getting all recipes"));
        }
    }

    override suspend fun getAllRecipesFromUserRemote(userID:Int): List<Recipe> {
        try{
            return service.getMyRecipes()
        }
        catch(exception:SQLException){
            throw (Exception("Error while getting all recipes"));
        }
    }
    override fun addRecipe(recipe: Recipe) {
//        allRecipes.add(recipe)
//        return recipe
        try{
//            throw SQLException("BLABLA")
         recipeDao.addRecipe(recipe)
        }
        catch(exception:SQLException){
            throw (Exception("Error while adding recipe"));
        }
    }

    override suspend fun addRecipeRemote(recipe:Recipe):Recipe{
        try{
            return service.addRecipe(recipe)
        }
        catch(exception:SQLException){
            throw (Exception("Error while adding recipe"));
        }
    }
    override fun deleteRecipe(recipeID: Int) {
//        var i = 0
//        for(r in allRecipes) {
//            if (r.id == recipeID)
//                break
//            i += 1
//        }
//        allRecipes.removeAt(i)
        try{
        recipeDao.deleteRecipe(recipeID)
        }
        catch(exception:SQLException){
            throw (Exception("Error while deleting recipe"));
        }
    }
    override suspend fun deleteRecipeRemote(recipeID:Int){
        try{
            service.deleteRecipe(recipeID)
        }
        catch(exception:SQLException){
            throw (Exception("Error while deleting recipe"));
        }
    }

    override fun updateRecipe(recipe: Recipe, newRecipe: Recipe) {
//        for(x in allRecipes){
//            if(x.id == recipe.id) {
//                x.ingrediet_name = newRecipe.ingrediet_name
//                x.modPreparare = newRecipe.modPreparare
//                x.imagineUrl = newRecipe.imagineUrl
//                x.calorii = newRecipe.calorii
//                x.idUser = newRecipe.idUser
//                x.timpPregatire = newRecipe.timpPregatire
//            }
//        }
        try{
        recipeDao.updateRecipe(newRecipe)
        }
        catch(exception:SQLException){
            throw (Exception("Error while updating recipe"));
        }
    }
    override suspend fun updateRecipeRemote(recipe:Recipe,newRecipe:Recipe){
        try{
            service.updateRecipe(recipe.id,newRecipe)
        }
        catch(exception:SQLException){
            throw (Exception("Error while updating recipe"));
        }
    }

    override fun getLastRecipe(): Recipe {
        try {
            return recipeDao.getLastRecipe()
        }
        catch(exception:SQLException){
            throw (Exception("Error while getting last recipe"));
    }
    }

    override suspend fun cacheRecipes(recipesFromServer:List<Recipe>){
        try{
            recipeDao.deleteAllRecipes()
            for( recipe in recipesFromServer)
                recipeDao.addRecipe(recipe)
        }
        catch (e:SQLException){
            throw (java.lang.Exception("Error while caching Recipe Data"))
        }
    }

    override fun getAllRecipesToBeSync(): List<Recipe> {
        return recipeDao.getAllOfflineAddedRecipes()
    }
    override fun ingredientsIDListForRecipe(recipeID:Int): List<Int> {
        return recipeDao.ingredientsIDListForRecipe(recipeID)
    }


}