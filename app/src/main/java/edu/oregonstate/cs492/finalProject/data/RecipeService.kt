package edu.oregonstate.cs492.finalProject.data

import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * NOTE: Code adapted from lecture examples (so are the comments- they're really helpful!)
 * This is a Retrofit service interface encapsulating communication with the MealDB API.
 */
interface RecipeService {
    /**
     * This method is used to query the MealDB API's "Lookup a single random meal
     * " method: www.themealdb.com/api/json/v1/1/random.php.  This is a suspending function,
     * so it must be called in a coroutine or within another suspending function.
     *
     * There are no parameters required for this call.
     *
     * @return Returns a Retrofit `Response<>` object that will contain a [RecipeItem] object
     *   if the API call was successful.
     */
    @GET("random.php")
    suspend fun getRandomRecipe(): Response<RecipeResponse>

    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        /**
         * This method can be called as `RecipeService.create()` to create an object
         * implementing the RecipeService interface and which can be used to make calls to
         * the MealDB API.
         */
        fun create(): RecipeService {
            val moshi = Moshi.Builder().build() // May need to add some adapter calls here
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(RecipeService::class.java)
        }
    }
}
