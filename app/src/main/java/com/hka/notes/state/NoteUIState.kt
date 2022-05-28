package com.hka.notes.state

import com.hka.notes.data.db.Note

data class NoteUIState(
    val selectedNotes: List<Note>
)