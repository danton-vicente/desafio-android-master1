package com.picpay.desafio.android.presentation.routes

sealed class ContactsRoutes(val route: String) {
    object ContactsScreenRoute : ContactsRoutes("ContactsScreen")
    object ContactsDetailsScreenRoute : ContactsRoutes("ContactsDetailsScreenRoute")
}
