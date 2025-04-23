package com.picpay.desafio.android.presentation

data class ContactDetailsScreenStrings(
    val copyWithSuccess: String,
    val commentTitle: String,
    val addCommentTitle: String,
    val addCommentButton: String,
    val addCommentError: String,
    val emptyComments: String,
    val errorOnLoadComments: String,
    val likes: (Int) -> String,
    val date: (String) -> String,
)
