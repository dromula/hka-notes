package com.hka.notes.data

import com.hka.notes.data.db.Note
import com.hka.notes.data.db.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    val allNotes: Flow<List<Note>> = noteDao.getAll()

    suspend fun getNote(id: Long): Note {
       return noteDao.getNote(id)
    }

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun removeNote(note: Note) {
        noteDao.delete(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }
}