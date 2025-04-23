package com.picpay.desafio.android.presentation

import java.util.Locale.getDefault

val PtContactsScreenStrings = ContactsScreenStrings(
    topbarTitle = "Contatos",
    lastUpdated = { time ->
        val dateFormat = java.text.SimpleDateFormat("dd/MM 'às' HH:mm", getDefault())
        dateFormat.format(java.util.Date(time))
        "Ultima atualização: ${dateFormat.format(java.util.Date(time))}"
    },
    noContacts = "Você ainda não tem nenhum contato.",
    connectionErrorTitle = "Ops! Parece que você não está conectado à internet.",
    serverErrorTitle = "Ops, algo de errado aconteceu aqui do nosso lado.",
    genericErrorTitle = "Ops. algo deu errado e não sabemos o que foi. Tente novamente.",
    okButtonText = "Ok, entendi.",
)
