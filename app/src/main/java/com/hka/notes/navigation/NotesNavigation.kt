package com.hka.notes.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(
    val routeTemplate: String,
    val arguments: List<NamedNavArgument> = listOf()
) {
    object NoteListRoute : Route("noteslist")

    object NoteItemRoute : Route(
        routeTemplate = "note/{id}",
        arguments = listOf(navArgument("id") {
            type = NavType.LongType
        })
    ) {
        fun createRoute(id: Long) = "note/$id"
    }

    object NoteEditRoute : Route(
        routeTemplate = "note/edit/{id}",
        arguments = listOf(navArgument("id") {
            type = NavType.LongType
        })
    ) {
        fun createRoute(id: Long) = "note/edit/$id"
    }

    object NoteAddRoute : Route("noteadd")
}