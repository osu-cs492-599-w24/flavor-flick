package edu.oregonstate.cs492.finalProject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.finalProject.data.RecipeListAppDatabase
import edu.oregonstate.cs492.finalProject.data.JournalEntry
import edu.oregonstate.cs492.finalProject.data.RecipeInfo
import edu.oregonstate.cs492.finalProject.data.RecipeInfoRepository
import kotlinx.coroutines.launch

class RecipeInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipeInfoRepository(
        RecipeListAppDatabase.getInstance(application).recipeInfoDao()
    )

    val savedRecipes: LiveData<List<RecipeInfo>> = repository.getAllRecentRecipe()


    fun getAllEntries(): LiveData<List<RecipeInfo>> {
        return repository.getAllRecentRecipe()
    }
    fun addNewRecipe(
        name: String,
        image: String,
        category: String,
        region: String,
        videoLink: String,
        recipeLink: String
    ) {
        viewModelScope.launch {
            repository.insertRecipe(RecipeInfo(name, image, category, region, videoLink, recipeLink))
        }
    }



//    fun removeBookmarkedRepo(repo: GitHubRepo) {
//        viewModelScope.launch {
//            repository.deleteBookmarkedRepo(repo)
//        }
//    }
}