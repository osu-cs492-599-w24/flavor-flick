package edu.oregonstate.cs492.finalProject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JournalEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun write(journalEntry: JournalEntry)

    @Delete
    suspend fun delete(journalEntry: JournalEntry)

    @Query("SELECT * FROM JournalEntry")
    fun getAllJournalEntries(): LiveData<List<JournalEntry>>
}
