package com.hka.notes.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hka.notes.data.NoteRepository
import com.hka.notes.data.db.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private var _notesState: MutableLiveData<NoteUIState> =
        MutableLiveData(DEFAULT_UI_STATE)
    val noteState: LiveData<NoteUIState> = _notesState

    val notes: LiveData<List<Note>> = noteRepository.allNotes

    fun addNoteByStrings(header: String, message: String) {
        viewModelScope.launch {
            noteRepository.addNote(Note(0, header, message, Date()))
        }
    }

    fun updateNote(note: Note, header: String, message: String) {
        viewModelScope.launch {
            noteRepository.updateNote(note.copy(header = header, message = message))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.removeNote(note)
        }
    }

    fun getById(id: Long): Note? {
        return notes.value?.first { note -> note.id == id  }
    }

    fun addSelectedNote(note: Note) {
        val currentUIState = _notesState.value
        if (currentUIState != null) {
            val currentNotes = currentUIState.selectedNotes
            _notesState.value = currentUIState.copy(
                selectedNotes = currentNotes.plus(note),
            )
        }
    }

    fun removeSelectedNote(note: Note) {
        val currentUIState = _notesState.value
        if (currentUIState != null) {
            val currentNotes = currentUIState.selectedNotes
            _notesState.value = currentUIState.copy(
                selectedNotes = currentNotes.minus(note),
            )
        }
    }

    fun deleteSelectedNotes() {
        noteState.value?.selectedNotes?.map {
            deleteNote(it)
            removeSelectedNote(it)
        }
    }

    fun notesInSelectedNotes(): Boolean {
        return noteState.value?.selectedNotes?.isNotEmpty() ?: false
    }

    fun noteInSelectedNotes(note: Note): Boolean {
        return noteState.value?.selectedNotes?.contains(note) ?: false
    }

    companion object {
        private val DEFAULT_UI_STATE = NoteUIState(
            selectedNotes = emptyList()
        )
    }
}