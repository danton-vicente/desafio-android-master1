package com.picpay.desafio.android.presentation.events

sealed class ContactsScreenNavigationEvents {
    object NavigateBack : ContactsScreenNavigationEvents()
    data class NavigateToContactDetails(val id: Int) : ContactsScreenNavigationEvents()
}
