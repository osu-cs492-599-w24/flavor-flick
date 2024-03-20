package edu.oregonstate.cs492.finalProject.ui

import JournalAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.JournalEntry
import kotlinx.coroutines.launch

class JournalFragment : Fragment(R.layout.add_journal_entries) {
    private val viewModel: JournalViewModel by viewModels()
    private lateinit var journalAdapter: JournalAdapter
    private lateinit var journalEntryTitle: EditText
    private lateinit var journalEntryEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        journalEntryTitle = view.findViewById(R.id.title_text_box)
        journalEntryEditText = view.findViewById(R.id.journal_text_box)
        saveButton = view.findViewById(R.id.save_button)
        cancelButton = view.findViewById(R.id.cancel_button)

        saveButton.visibility = View.GONE
        cancelButton.visibility = View.GONE

        journalEntryEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                saveButton.visibility = View.VISIBLE
                cancelButton.visibility = View.VISIBLE
            } else {
                saveButton.visibility = View.GONE
                cancelButton.visibility = View.GONE
            }
        }

        saveButton.setOnClickListener {
            val journalEntry = journalEntryEditText.text.toString()
            val journalTitle = journalEntryTitle.text.toString()
            viewModel.saveJournalEntry(journalTitle, journalEntry)
            journalEntryTitle.text.clear()
            journalEntryTitle.clearFocus()
            journalEntryEditText.text.clear()
            journalEntryEditText.clearFocus()

            findNavController().navigateUp()
        }

        cancelButton.setOnClickListener {
            journalEntryTitle.text.clear()
            journalEntryTitle.clearFocus()
            journalEntryEditText.text.clear()
            journalEntryEditText.clearFocus()
            saveButton.visibility = View.GONE
            cancelButton.visibility = View.GONE
        }

        journalAdapter = JournalAdapter()
        journalAdapter.setOnDeleteClickListener(object : JournalAdapter.OnDeleteClickListener {
            override fun onDeleteClick(entry: JournalEntry) {
                viewModel.viewModelScope.launch {
                    Log.d("Close Button 3", "Successfully clicked in onDeleteClick");
                    viewModel.delete(entry)
                }
            }
        })

        viewModel.getAllEntries().observe(viewLifecycleOwner) { journalEntries: List<JournalEntry> ->
            val reversedEntries = journalEntries.reversed()
            journalAdapter.updateEntries(reversedEntries)
        }
    }
}
