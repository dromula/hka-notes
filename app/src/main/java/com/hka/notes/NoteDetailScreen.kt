package com.hka.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hka.notes.data.db.Note
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteDetailScreen(
    noteId: Note,
    noteViewModel: NoteViewModel,
    navigateBack: () -> Unit,
    navigateEdit: () -> Unit
) {
    val notes by noteViewModel.notes.observeAsState()
    val note = notes?.find{ it.id == noteId.id } ?: Note(0, "", "", Date())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(note.header) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navigateEdit()
                    }) {
                        Icon(Icons.Filled.Edit, "editIcon")
                    }
                    IconButton(onClick = {
                        noteViewModel.deleteNote(note)
                        navigateBack()
                    }) {
                        Icon(Icons.Filled.Delete, "deleteIcon")
                    }
                }
            )
        },

        ) {
        // Screen content
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
            Text(
                text = formatter.format(note.createdAt),
                style = TextStyle(fontSize = 14.sp, color = Color.DarkGray),
            )

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

            val scroll = rememberScrollState(0)
            Text(text = note.message, modifier = Modifier.padding(horizontal = 15.dp).verticalScroll(scroll))
        }
    }
}