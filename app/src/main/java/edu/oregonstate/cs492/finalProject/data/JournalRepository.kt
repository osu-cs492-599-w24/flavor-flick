package edu.oregonstate.cs492.finalProject.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val entryDao: JournalEntryDao) {

    val allJournalEntries: LiveData<List<JournalEntry>> = entryDao.getAllJournalEntries()


    suspend fun insert(entry: JournalEntry) {
        entryDao.write(entry)
    }

    suspend fun delete(entry: JournalEntry) {
        entryDao.delete(entry)
    }
}
