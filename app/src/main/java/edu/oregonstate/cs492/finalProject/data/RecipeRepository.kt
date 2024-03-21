package edu.oregonstate.cs492.finalProject.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * NOTE: Code adapted from lecture examples
 * This class manages data operations associated with the MealDB random recipe API endpoint.
 */
class RecipeRepository(
    private val service: RecipeService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     * This method fetches a random recipe from the API.
     *
     * @return Returns a Kotlin Result object wrapping the [RecipeItem] object that
     *   represents the fetched recipe. If the API query is unsuccessful for some reason, the
     *   Exception associated with the Result object will provide more info about why the query
     *   failed.
     */
    suspend fun getRandomRecipe(): Result<RecipeItem?> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getRandomRecipe()
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        Result.success(meals[0]) // Assuming the API returns only one random meal (which it does)
                    } else {
                        Result.failure(Exception("No recipe found"))
                    }
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
