package com.hka.notes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hka.notes.data.db.Note
import com.hka.notes.state.NoteViewModel

@Composable
fun NoteAddView(noteViewModel: NoteViewModel, note: Note?, navigateBack: () -> Unit) {
    var header by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var editNote by remember { mutableStateOf(false) }
    var title = "Notiz hinzufügen"

    if (note != null) {
        editNote = true
        message = note.message
        header = note.header
        title = "Notiz bearbeiten"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = navigateBack ) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    if (editNote) {
                        IconButton(
                            onClick = {
                                if (note != null) {
                                    noteViewModel.updateNote(note, header, message)
                                }
                                navigateBack()
                            },
                            enabled = addNoteEnabled(header, message)
                        ) {
                            Icon(Icons.Filled.Done, "doneIcon")
                        }
                    }
                    else {
                        IconButton(
                            onClick = {
                                noteViewModel.addNoteByStrings(header, message)
                                navigateBack()
                            },
                            enabled = addNoteEnabled(header, message)
                        ) {
                            Icon(Icons.Filled.Add, "createIcon")
                        }
                    }
                }
            )
        },
    ) {
        NoteFormula(header, message, onHeaderChange = { header = it }, onMessageChange = { message = it })
    }
}

@Composable
fun NoteFormula(header: String, message: String, onHeaderChange: (String) -> Unit, onMessageChange: (String) -> Unit) {
    Column(
        Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {

        val textModifier = Modifier.padding(horizontal = 16.dp)
        val textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Text (
            text = "Überschrift",
            style = textStyle,
            modifier = textModifier
        )

        TextField(
            value = header,
            label = { Text("Titel der Notiz") },
            modifier = Modifier.padding(15.dp).fillMaxWidth().height(55.dp),
            onValueChange = onHeaderChange)

        Spacer(Modifier.height(8.dp))

        Text (
            text = "Beschreibung",
            style = textStyle,
            modifier = textModifier
        )

        TextField(
            value = message,
            label = { Text("Deine Notiz") },
            modifier = Modifier.padding(15.dp).fillMaxWidth().height(400.dp),
            onValueChange = onMessageChange
        )
    }
}

fun addNoteEnabled(header: String, message: String): Boolean {
    return header.isNotEmpty() && message.isNotEmpty()
}