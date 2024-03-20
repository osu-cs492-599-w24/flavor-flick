import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.JournalEntry
import android.util.Log


class JournalAdapter(private var entries: List<JournalEntry> = emptyList()) :
    RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    private var selectedEntry: JournalEntry? = null
    private var onDeleteClickListener: OnDeleteClickListener? = null

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

    fun getSelectedEntry(): JournalEntry? {
        return selectedEntry
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(entry: JournalEntry)
    }

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_tv)
        private val entryTextView: TextView = itemView.findViewById(R.id.journal_entry_tv)
        private val closeButton: ImageButton = itemView.findViewById(R.id.delete_button)

        init {
            closeButton.setOnClickListener {
                Log.d("Close Button", "Successfully clicked");
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener?.onDeleteClick(entries[position])
                    Log.d("Close Button 2", "Successfully clicked again");
                }
            }
        }

        fun bind(entry: JournalEntry) {
            titleTextView.text = entry.title
            entryTextView.text = entry.entryText
        }
    }

}

