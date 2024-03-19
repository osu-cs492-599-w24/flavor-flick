package edu.oregonstate.cs492.finalProject.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.oregonstate.cs492.finalProject.R

class ViewEntryFragment : Fragment(R.layout.fragment_journal) {
    private lateinit var addButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton = view.findViewById(R.id.add_entry_button)

        addButton.setOnClickListener {
            val direction = ViewEntryFragmentDirections.addJournalEntries()
            findNavController().navigate(direction)
        }

        val titleTextView1 = view.findViewById<TextView>(R.id.title_tv)
        val entryTextView1 = view.findViewById<TextView>(R.id.journal_entry_tv)

        titleTextView1.text = "Sample Entry 1"
        entryTextView1.text = "Random sample text for entry 1"
    }
}
