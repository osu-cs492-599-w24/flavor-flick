package edu.oregonstate.cs492.finalProject.data

class RecipeInfoRepository (
    private val dao: RecipeInfoDao
){
    suspend fun insertRecipe(repo: RecipeInfo) = dao.insert(repo)

    fun getAllRecipe() = dao.getTitle()
}