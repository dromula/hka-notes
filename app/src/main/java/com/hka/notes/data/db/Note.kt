package com.hka.notes.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val header: String,
    val message: String,
    val createdAt: Date
) {
}
