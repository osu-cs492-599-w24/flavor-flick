package edu.oregonstate.cs492.finalProject.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.oregonstate.cs492.finalProject.R

class ViewEntryFragment : Fragment(R.layout.fragment_journal) {
//    private lateinit var journalEntryTextView: EditText
    private lateinit var addButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        journalEntryTextView = view.findViewById(R.id.journal_text_box) as EditText
        addButton = view.findViewById(R.id.add_entry_button)

        addButton.setOnClickListener {
            val direction = ViewEntryFragmentDirections.addJournalEntries()
            findNavController().navigate(direction)

        }
    }

//    fun displayJournalEntry(journalEntry: String) {
//        journalEntryTextView.setText(journalEntry)
//    }
}
