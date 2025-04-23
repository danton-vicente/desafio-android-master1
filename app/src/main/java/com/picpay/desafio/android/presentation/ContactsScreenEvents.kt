package com.picpay.desafio.android.presentation

sealed class ContactsScreenEvents {
    object OnBackPressed : ContactsScreenEvents()
    object OnDismissNetworkErrorBottomSheet : ContactsScreenEvents()
    object OnDismissServerErrorBottomSheet : ContactsScreenEvents()
    object OnDismissGenericErrorBottomSheet : ContactsScreenEvents()
}
