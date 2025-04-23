package com.picpay.desafio.android.presentation

sealed class ContactDetailsScreenEvents {
    object OnBackPressed : ContactDetailsScreenEvents()
    object OnDismissErrorBottomSheet : ContactDetailsScreenEvents()
    object OnAddCommentClicked: ContactDetailsScreenEvents()
    data class OnLikeCommentClicked(val index: Int): ContactDetailsScreenEvents()
    object OnAddCommentConfirmed: ContactDetailsScreenEvents()
    data class OnCopyWalletClicked(val wallet: ContactDetailsWallets): ContactDetailsScreenEvents()
}

enum class ContactDetailsWallets {
    Bitcoin,
    Ethereum,
    Litecoin,
}
