package edu.oregonstate.cs492.finalProject.data

import androidx.lifecycle.LiveData

class RecipeInfoRepository (private val dao: RecipeInfoDao) {

    val allRecipes: LiveData<List<RecipeInfo>> = dao.getAllRecipe()

    suspend fun insertRecipe(recipe: RecipeInfo) = dao.insert(recipe)

    suspend fun deleteRecipe(recipe: RecipeInfo) = dao.delete(recipe)

    fun getAllRecentRecipe() = dao.getAllRecipe()
}