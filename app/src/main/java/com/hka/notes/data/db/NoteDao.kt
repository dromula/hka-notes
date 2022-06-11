package com.hka.notes.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note where id = :noteId LIMIT 1")
    suspend fun getNote(noteId: Long): Note

    @Update
    suspend fun update(note: Note)
}