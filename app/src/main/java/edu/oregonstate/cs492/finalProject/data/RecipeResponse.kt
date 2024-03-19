package edu.oregonstate.cs492.finalProject.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * This class is used to help parse the JSON recipe data returned by the MealDB API's
 * "Lookup a single random meal" endpoint.
 * This class represents the list of returned meals (only one is returned lol).
 */
@JsonClass(generateAdapter = true)
data class RecipeResponse(
    @Json(name = "meals") val meals: List<RecipeItem>?
    // Ok ya I know I don't need to do the @Json thing but it looks cool ok
)
