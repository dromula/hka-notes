package com.hka.notes.state

import androidx.lifecycle.*
import com.hka.notes.data.NoteRepository
import com.hka.notes.data.db.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val _notesFlow = MutableStateFlow(DEFAULT_UI_STATE)
    val notesFlow = _notesFlow.asStateFlow()

    init {
        loadNotes()
    }

    fun loadNotes() {
        _notesFlow.update { prev -> prev.copy(isLoading = true) }
        viewModelScope.launch {
            noteRepository.allNotes.collect {
                _notesFlow.update {
                        prev -> prev.copy(notes = it, isLoading = false)
                }
            }
        }
    }

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

    fun getById(id: Long): Note {
        return notesFlow.value.notes.first { note -> note.id == id  }
    }

    fun addSelectedNote(note: Note) {
        val currentUIState = _notesFlow.value
        val currentNotes = currentUIState.selectedNotes
        _notesFlow.value = currentUIState.copy(
            selectedNotes = currentNotes.plus(note),
        )
    }

    fun removeSelectedNote(note: Note) {
        val currentUIState = _notesFlow.value
        val currentNotes = currentUIState.selectedNotes
        _notesFlow.value = currentUIState.copy(
            selectedNotes = currentNotes.minus(note),
        )
    }

    fun deleteSelectedNotes() {
        notesFlow.value.selectedNotes.map {
            deleteNote(it)
            removeSelectedNote(it)
        }
    }

    fun notesInSelectedNotes(): Boolean {
        return notesFlow.value.selectedNotes.isNotEmpty()
    }

    fun noteInSelectedNotes(note: Note): Boolean {
        return notesFlow.value.selectedNotes.contains(note)
    }

    companion object {
        private val DEFAULT_UI_STATE = NoteUIState(
            selectedNotes = emptyList(),
            notes = emptyList(),
            isLoading = false
        )
    }
}