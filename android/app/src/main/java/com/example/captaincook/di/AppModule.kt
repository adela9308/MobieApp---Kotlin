package com.example.captaincook.di

import android.content.Context
import androidx.room.Room
import com.example.captaincook.add_recipe.usecase.*
import com.example.captaincook.details.usecase.FindOneRecipeUseCase
import com.example.captaincook.details.usecase.FindOneRecipeUseCaseImpl
import com.example.captaincook.ingredients.repo.IngredientsRepository
import com.example.captaincook.ingredients.repo.IngredientsRepositoryImpl
import com.example.captaincook.ingredients.usecase.GetAllIngredientsUseCase
import com.example.captaincook.ingredients.usecase.GetAllIngredientsUseCaseImpl
import com.example.captaincook.my_recipes.usecase.GetAllRecipesForUserUseCase
import com.example.captaincook.my_recipes.usecase.GetAllRecipesForUserUseCaseImpl
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepository
import com.example.captaincook.recipe_ingredients.repo.Recipe_IngredientsRepositoryImpl
import com.example.captaincook.recipe_ingredients.usecase.GetAllRecipeIngredientsUseCase
import com.example.captaincook.recipe_ingredients.usecase.GetAllRecipeIngredientsUseCaseImpl
import com.example.captaincook.recipe_ingredients.usecase.IngredientsListForRecipeUseCase
import com.example.captaincook.recipe_ingredients.usecase.IngredientsListForRecipeUseCaseImpl
import com.example.captaincook.recipes.repo.RecipeRoomDatabase
import com.example.captaincook.recipes.repo.RecipesRepository
import com.example.captaincook.recipes.repo.RecipesRepositoryImpl
import com.example.captaincook.recipes.service.RecipeService
import com.example.captaincook.recipes.service.RecipesService
import com.example.captaincook.recipes.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()//vai da astea iara le ai luat ca maimuta n
            .baseUrl("http://192.168.100.7:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }



    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RecipeRoomDatabase::class.java,
        "ingredients_database"
    ).allowMainThreadQueries().build()// The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: RecipeRoomDatabase) = db.recipeDao() // The reason we can implement a Dao for the database



    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        @Singleton
        fun provideRecipesRepository(repo: RecipesRepositoryImpl): RecipesRepository

        @Binds
        @Singleton
        fun provideGetRecipesUseCase(uc: GetRecipesUseCaseImpl): GetRecipesUseCase

        @Binds
        @Singleton
        fun provideGetRecipesRemoteUseCase(uc: GetRecipesRemoteUseCaseImpl): GetRecipesRemoteUseCase


        @Binds
        @Singleton
        fun provideFindOneRecipeUseCase(uc: FindOneRecipeUseCaseImpl): FindOneRecipeUseCase


        @Binds
        @Singleton
        fun provideRecipesIngredientsRepository(repo: Recipe_IngredientsRepositoryImpl): Recipe_IngredientsRepository

        @Binds
        @Singleton
        fun provideIngredientsListForRecipeUseCase(uc: IngredientsListForRecipeUseCaseImpl): IngredientsListForRecipeUseCase

        @Binds
        @Singleton
        fun provideIngredientsRepository(repo: IngredientsRepositoryImpl): IngredientsRepository


        @Binds
        @Singleton
        fun provideGetAllRecipeIngredientsUseCase(uc: GetAllRecipeIngredientsUseCaseImpl): GetAllRecipeIngredientsUseCase


        @Binds
        @Singleton
        fun provideGetAllRecipesFromUserUseCase(uc: GetAllRecipesForUserUseCaseImpl): GetAllRecipesForUserUseCase


        @Binds
        @Singleton
        fun provideGetAllIngredientsUseCase(uc: GetAllIngredientsUseCaseImpl): GetAllIngredientsUseCase


        @Binds
        @Singleton
        fun provideAddRecipeUseCase(uc: AddRecipeUseCaseImpl): AddRecipeUseCase


        @Binds
        @Singleton
        fun provideAddRecipeRemoteUseCase(uc: AddRecipeRemoteUseCaseImpl): AddRecipeRemoteUseCase



        @Binds
        @Singleton
        fun provideDeleteRecipeUseCase(uc: DeleteRecipeUseCaseImpl): DeleteRecipeUseCase


        @Binds
        @Singleton
        fun provideUpdateRecipeUseCase(uc: UpdateRecipeUseCaseImpl): UpdateRecipeUseCase

        @Binds
        @Singleton
        fun provideUpdateRecipeRemoteUseCase(uc: UpdateRecipeRemoteUseCaseImpl): UpdateRecipeRemoteUseCase



        @Binds
        @Singleton
        fun provideGetRecipeListSizeUseCase(uc: GetRecipesListSizeUseCaseImpl): GetRecipesListSizeUseCase


        @Binds
        @Singleton
        fun provideSyncDataUseCaseImpl(uc: SyncDataUseCaseImpl): SyncDataUseCase


    }

}