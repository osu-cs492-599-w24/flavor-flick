package edu.oregonstate.cs492.finalProject.data

import androidx.lifecycle.LiveData

class JournalRepository(private val entryDao: JournalEntryDao) {
    val allJournalEntries: LiveData<List<JournalEntry>> = entryDao.getAllJournalEntries()

    suspend fun insert(entry: JournalEntry) {
        entryDao.write(entry)
    }

    suspend fun delete(entry: JournalEntry) {
        entryDao.delete(entry)
    }
}
