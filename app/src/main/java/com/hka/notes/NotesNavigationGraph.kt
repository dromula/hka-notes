package com.hka.notes.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hka.notes.*

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
            var id = backStackEntry.arguments?.getLong("id")
            var note = noteViewModel.getById(id!!)

            NoteDetailScreen(
                noteId = note,
                noteViewModel =  noteViewModel,
                navigateBack = { navController.popBackStack(route = Route.NoteListRoute.routeTemplate, inclusive = false) },
                navigateEdit = { navController.navigate(Route.NoteEditRoute.createRoute(id))}
            )
        }

        composable(Route.NoteEditRoute.routeTemplate, Route.NoteEditRoute.arguments) { backStackEntry ->
            var id = backStackEntry.arguments?.getLong("id")
            var note = noteViewModel.getById(id!!)
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