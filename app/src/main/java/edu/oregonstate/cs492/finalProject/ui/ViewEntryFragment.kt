package edu.oregonstate.cs492.finalProject.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.JournalEntry


class ViewEntryFragment : Fragment(R.layout.fragment_journal) {
    private lateinit var addButton: Button
    private lateinit var journalAdapter: JournalAdapter
    private val viewModel: JournalViewModel by viewModels()

    private lateinit var journalEntriesRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton = view.findViewById(R.id.add_entry_button)
        journalAdapter = JournalAdapter(emptyList())
        journalEntriesRV = view.findViewById(R.id.recycler_view)
        journalEntriesRV.layoutManager = LinearLayoutManager(requireContext())
        journalEntriesRV.setHasFixedSize(true)
        journalEntriesRV.adapter = journalAdapter

        viewModel.getAllEntries().observe(viewLifecycleOwner) { journalEntries: List<JournalEntry> ->
            // gets most recent entry first then displays it
            val reversedEntries = journalEntries.reversed()
            journalAdapter.updateEntries(reversedEntries)
        }

        // if clicked, brings you to separate page to add entry
        addButton.setOnClickListener {
            val direction = ViewEntryFragmentDirections.addJournalEntries()
            findNavController().navigate(direction)
        }
    }

}
