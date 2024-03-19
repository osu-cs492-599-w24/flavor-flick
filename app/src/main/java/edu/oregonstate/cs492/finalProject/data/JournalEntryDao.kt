package edu.oregonstate.cs492.finalProject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun write(journalEntry: JournalEntry)

    @Query("SELECT title FROM JournalEntry")
    fun getTitle(): Flow<List<String>>

    @Query("SELECT entryText FROM JournalEntry")
    fun getEntry(): Flow<List<String>>
}
