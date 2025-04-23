package com.picpay.desafio.android.presentation

import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData

data class ContactDetailsScreenState(
    val isLoadingUserData: Boolean = true,
    val showLoadingComments: Boolean = true,
    val showLoadingAddComments: Boolean = false,
    val comments: List<CommentsData> = emptyList(),
    val userData: UserData = UserData(),
)
