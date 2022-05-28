package com.hka.notes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hka.notes.state.NoteViewModel
import com.hka.notes.ui.NoteAddView
import com.hka.notes.ui.NoteDetailScreen
import com.hka.notes.ui.NoteListView

@Composable
fun NotesNavigationGraph(
    noteViewModel: NoteViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.NoteListRoute.routeTemplate
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Route.NoteListRoute.routeTemplate) {
            NoteListView(noteViewModel = noteViewModel, navController=navController)
        }

        composable(Route.NoteItemRoute.routeTemplate, Route.NoteItemRoute.arguments) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            if( id != null) {
                NoteDetailScreen(
                    noteId = id,
                    noteViewModel = noteViewModel,
                    navigateBack = {
                        navController.popBackStack(
                            route = Route.NoteListRoute.routeTemplate,
                            inclusive = false
                        )
                    },
                    navigateEdit = { navController.navigate(Route.NoteEditRoute.createRoute(id)) }
                )
            }
        }

        composable(Route.NoteEditRoute.routeTemplate, Route.NoteEditRoute.arguments) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            val note = noteViewModel.getById(id!!)
            NoteAddView(noteViewModel = noteViewModel, note = note) {
                navController.popBackStack()
            }
        }

        composable(Route.NoteAddRoute.routeTemplate) {
            NoteAddView(noteViewModel = noteViewModel, note = null) {
                navController.popBackStack()
            }
        }
    }

}