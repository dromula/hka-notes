package com.hka.notes

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.hka.notes.ui.theme.HkanotesTheme
import androidx.compose.material.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.hka.notes.state.NoteViewModel
import com.hka.notes.navigation.NotesNavigationGraph
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiActivity : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HkanotesTheme {
                Scaffold {
                    NoteApp()
                }
            }
        }

    }
}

@Composable
fun NoteApp() {
    val noteViewModel: NoteViewModel = hiltViewModel()

    NotesNavigationGraph(noteViewModel = noteViewModel)
}