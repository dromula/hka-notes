package com.hka.notes

import com.hka.notes.data.db.Note

data class NoteUIState(
    val notes: List<Note>,
    val isLoading: Boolean,
    val selectedNotes: List<Note>
)