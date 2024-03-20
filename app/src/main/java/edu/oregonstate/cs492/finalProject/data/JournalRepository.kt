package edu.oregonstate.cs492.finalProject.data

import kotlinx.coroutines.flow.Flow

class JournalRepository(private val entryDao: JournalEntryDao) {

    val allTitles: Flow<List<String>> = entryDao.getTitle()
    val allEntries: Flow<List<String>> = entryDao.getEntry()


    suspend fun insert(entry: JournalEntry) {
        entryDao.write(entry)
    }
}
