package edu.oregonstate.cs492.finalProject.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * This class is used to help parse the JSON recipe data returned by the TheMealDB API's
 * "Lookup a single random meal" endpoint.
 */
@JsonClass(generateAdapter = true)
data class RecipeItem(
    @Json(name = "name") val strMeal: String,
    @Json(name = "image") val strMealThumb: String,
    @Json(name = "category")  val strCategory: String,
    @Json(name = "region") val strArea: String,
    @Json(name = "videoLink") val strYoutube: String,
    @Json(name = "recipeLink") val strSource: String
)
