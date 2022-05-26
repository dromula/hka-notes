package com.hka.notes

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hka.notes.data.db.Note
import java.text.SimpleDateFormat

@Composable
fun NoteListView(noteViewModel: NoteViewModel, navController: NavHostController) {
    val noteUIState by noteViewModel.noteState.observeAsState()
    val selectedNotes = noteUIState?.selectedNotes ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deine Notizen") },
                actions = {
                    IconButton(onClick = { noteViewModel.deleteSelectedNotes() }) {
                        if (selectedNotes.isNotEmpty()) {
                            Icon(Icons.Filled.Delete, "deleteIcon")
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Route.NoteAddRoute.routeTemplate)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_add),
                    contentDescription = "Add"
                )
            }
        },
        // Defaults to FabPosition.End
        floatingActionButtonPosition = FabPosition.End
    ) {
        // Screen content
        NoteList(noteViewModel, navController, noteUIState)
    }
}

@Composable
fun NoteList(
    noteViewModel: NoteViewModel,
    navController: NavHostController,
    noteUIState: NoteUIState?
) {
    val notes by noteViewModel.notes.observeAsState(emptyList())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {

        items(
            items = notes.sortedByDescending { it.createdAt?.time },
            key = { note -> note.id }
        ) { note ->
            var id = note.id
            NoteListItem(
                noteViewModel = noteViewModel,
                note = note,
                onClick = {
                    navController.navigate(Route.NoteItemRoute.createRoute(id))
                },
                noteUIState = noteUIState
            )
        }
    }

}

@Composable
fun NoteListItem(
    noteViewModel: NoteViewModel,
    note: Note,
    onClick: () -> Unit,
    noteUIState: NoteUIState?
) {
    Column(
        modifier = Modifier
            .height(60.dp)
            .background(getColor(noteViewModel, note))
            .fillMaxWidth()
            .clickable { onClick() }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        noteViewModel.addSelectedNote(note)
                    },
                    onTap = {
                        if (noteViewModel.noteInSelectedNotes(note)) {
                            noteViewModel.removeSelectedNote(note)
                        } else if (noteViewModel.notesInSelectedNotes()) {
                            noteViewModel.addSelectedNote(note)
                        } else {
                            onClick()
                        }
                    },
                )
            },
        verticalArrangement = Arrangement.Center
    ) {
        //Spacer(Modifier.height(8.dp))
        val textModifier = Modifier.padding(horizontal = 16.dp)
        Text(
            text = "${note.header}",
            style = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = textModifier
        )

        val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
        Text(
            text = formatter.format(note.createdAt),
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
            modifier = textModifier
        )
    }
}

fun getColor(noteViewModel: NoteViewModel, note: Note): Color {
    return if (noteViewModel.noteInSelectedNotes(note)) {
        Color.LightGray
    } else {
        Color.White
    }
}