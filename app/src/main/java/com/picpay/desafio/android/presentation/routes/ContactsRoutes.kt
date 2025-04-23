package com.vidishopper.VidiApp.presentation.ui.exampleCompose.routes

sealed class ContactsRoutes(val route: String) {
    object ContactsScreenRoute : ContactsRoutes("ContactsScreen")
}
