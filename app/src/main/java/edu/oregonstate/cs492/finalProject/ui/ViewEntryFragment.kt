package edu.oregonstate.cs492.finalProject.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.JournalEntry

class ViewEntryFragment : Fragment(R.layout.fragment_journal) {
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var journalAdapter: JournalAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton = view.findViewById(R.id.add_entry_button)
        recyclerView = view.findViewById(R.id.recycler_view)

        journalAdapter = JournalAdapter(emptyList())

        addButton.setOnClickListener {
            val direction = ViewEntryFragmentDirections.addJournalEntries()
            findNavController().navigate(direction)
        }

        val dummyEntries = listOf(
            JournalEntry("Entry 1", "sample text for entry 1", ""),
            JournalEntry("Entry 2", "sample text for entry 2", ""),
            JournalEntry("Entry 3", "sample text for entry 3", "")
        )

        recyclerView.adapter = JournalAdapter(dummyEntries)
    }
}
