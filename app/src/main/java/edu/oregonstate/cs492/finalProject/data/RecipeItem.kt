package edu.oregonstate.cs492.finalProject.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * NOTE: Code adapted from lecture examples
 * This class is used to help parse the JSON recipe data returned by the MealDB API's
 * "Lookup a single random meal" endpoint.
 * This class represents a single recipe in the list of returned meals.
 */
@JsonClass(generateAdapter = true)
data class RecipeItem(
    @Json(name = "strMeal") val name: String,
    @Json(name = "strMealThumb") val image: String,
    @Json(name = "strCategory") val category: String,
    @Json(name = "strArea") val region: String,
    @Json(name = "strYoutube") val videoLink: String,
    @Json(name = "strSource") val recipeLink: String
)
