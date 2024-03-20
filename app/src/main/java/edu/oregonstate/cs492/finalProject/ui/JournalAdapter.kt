package edu.oregonstate.cs492.finalProject.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.JournalEntry

class JournalAdapter(private var entries: List<JournalEntry> = emptyList()) :
    RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.journal_entry_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    fun updateEntries(newEntries: List<JournalEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_tv)
        private val entryTextView: TextView = itemView.findViewById(R.id.journal_entry_tv)

        fun bind(entry: JournalEntry) {
            titleTextView.text = entry.title
            entryTextView.text = entry.entryText
        }
    }
}
