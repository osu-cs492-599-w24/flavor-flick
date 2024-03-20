package edu.oregonstate.cs492.finalProject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeInfo (
    @PrimaryKey
    val title: String,
    val image: String,
    val category: String,
    val region: String,
    val videoLink: String,
    val recipeLink: String
)