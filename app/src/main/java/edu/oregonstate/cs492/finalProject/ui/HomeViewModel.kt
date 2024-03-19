package edu.oregonstate.cs492.finalProject.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.finalProject.data.RecipeRepository
import edu.oregonstate.cs492.finalProject.data.RecipeItem
import edu.oregonstate.cs492.finalProject.data.RecipeService
import kotlinx.coroutines.launch

/**
 * This ViewModel class manages the recipe data for the HomeFragment UI.
 */
class HomeViewModel : ViewModel() {
    private val repository = RecipeRepository(RecipeService.create())

    /**
     * The most recent recipe fetched from the API is stored in this private property.
     * This recipe is exposed to the outside world in immutable form via the public `recipe`
     * property below.
     */
    private val _recipe = MutableLiveData<RecipeItem?>()

    /**
     * This LiveData provides the most recent recipe fetched from the API.
     * It is null if there are no recipes available.
     */
    val recipe: LiveData<RecipeItem?> = _recipe

    /**
     * The current error for the most recent API call is stored in this private property.
     * This error is exposed to the outside world in immutable form via the public `error`
     * property below.
     */
    private val _error = MutableLiveData<Throwable?>()

    /**
     * This LiveData property provides the error associated with the most recent API call,
     * if there is one. If there was no error associated with the most recent API call, it will be null.
     */
    val error: LiveData<Throwable?> = _error

    /**
     * This method triggers a new call to fetch a random recipe from the API.
     * It updates the public properties of this ViewModel class to reflect the current status
     * of the API call.
     */
    fun fetchNewRecipe() {
        /**
         * Launch a new coroutine to execute the API call. The coroutine is tied to the
         * lifecycle of this ViewModel using `viewModelScope`.
         */
        viewModelScope.launch {
            try {
                val result = repository.getRandomRecipe()
                Log.d("RecipeRepository", "JSON response: $result")
                if (result.isSuccess) {
                    _recipe.value = result.getOrNull()
                } else {
                    _error.value = result.exceptionOrNull()
                }
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Error fetching recipe", e)
                _error.value = e
            }
        }
    }
}
