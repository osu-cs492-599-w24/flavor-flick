package edu.oregonstate.cs492.finalProject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecipeInfo::class], version = 1)
abstract class RecipeListAppDatabase : RoomDatabase(){
    abstract fun recipeInfoDao() : RecipeInfoDao

    companion object {
        @Volatile var instance: RecipeListAppDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                RecipeListAppDatabase::class.java,
                "recipe-info-db"
            ).build()

        fun getInstance(context: Context) : RecipeListAppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

    }
}