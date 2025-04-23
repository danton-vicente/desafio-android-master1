package com.picpay.desafio.android.presentation.events

sealed class ContactsScreenEvents {
    object OnBackPressed : ContactsScreenEvents()
    object OnDismissErrorBottomSheet : ContactsScreenEvents()
    data class OnCardClicked(val index: Int) : ContactsScreenEvents()
}
