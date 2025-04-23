package com.picpay.desafio.android.presentation.states

import com.picpay.desafio.android.domain.model.UserData

data class ContactsScreenState(
    val isLoading: Boolean = true,
    val lastUpdateDate: Long = System.currentTimeMillis(),
    val contacts: List<UserData> = emptyList(),
    val showError: Boolean = false,
)
