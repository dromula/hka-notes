package com.hka.notes

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hka.notes.ui.theme.HkanotesTheme
import androidx.compose.material.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hka.notes.ui.theme.NotesNavigationGraph
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiActivity: Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HkanotesTheme {
                // A surface container using the 'background' color from the theme
                MaterialTheme {
                    Scaffold {
                        NoteApp()
                    }
                }
            }
        }
    }
}

@Composable
fun NoteApp() {
    val noteViewModel: NoteViewModel = hiltViewModel()

    NotesNavigationGraph(noteViewModel = noteViewModel )
}