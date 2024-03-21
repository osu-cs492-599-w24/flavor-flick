package edu.oregonstate.cs492.finalProject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.finalProject.data.AppDatabase
import edu.oregonstate.cs492.finalProject.data.JournalEntry
import edu.oregonstate.cs492.finalProject.data.JournalRepository
import kotlinx.coroutines.launch

/**
 * This is a ViewModel class that holds user inputted journal entries and displays it
 */
class JournalViewModel(application: Application) : AndroidViewModel(application){
    private val repository: JournalRepository = JournalRepository(AppDatabase.getInstance(application).journalEntryDao())

    fun saveJournalEntry(title: String, entryText: String) {
        viewModelScope.launch {
            val journalEntry = JournalEntry(title, entryText)
            repository.insert(journalEntry)
        }
    }

    fun getAllEntries(): LiveData<List<JournalEntry>> {
        return repository.allJournalEntries
    }

    suspend fun delete(entry: JournalEntry) {
        repository.delete(entry)
    }

    private val _error = MutableLiveData<Throwable?>(null)

    /**
     * This property provides the error associated with the most recent API query, if there is
     * one.  If there was no error associated with the most recent API query, it will be null.
     */
    val error: LiveData<Throwable?> = _error

    /*
     * The current loading state is stored in this private property.  This loading state is exposed
     * to the outside world in immutable form via the public `loading` property below.
     */
    private val _loading = MutableLiveData<Boolean>(false)

    /**
     * This property indicates the current loading state of an API query.  It is `true` if an
     * API query is currently being executed or `false` otherwise.
     */
    val loading: LiveData<Boolean> = _loading
}