package edu.oregonstate.cs492.finalProject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeInfo)

    @Delete
    suspend fun delete(recipe: RecipeInfo)

    @Query("SELECT * FROM RecipeInfo")
    fun getAllRecipe(): LiveData<List<RecipeInfo>>

}
