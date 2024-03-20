package edu.oregonstate.cs492.finalProject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class JournalEntry(
    @PrimaryKey
    val title: String,
    val entryText: String,
)