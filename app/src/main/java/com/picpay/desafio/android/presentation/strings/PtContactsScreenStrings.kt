package com.picpay.desafio.android.presentation.strings

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale.getDefault

val PtContactsScreenStrings = ContactsScreenStrings(
    topbarTitle = "Contatos",
    lastUpdated = { time ->
        val dateFormat = SimpleDateFormat("dd/MM 'às' HH:mm", getDefault())
        dateFormat.format(Date(time))
        "Ultima atualização: ${dateFormat.format(Date(time))}"
    },
    noContacts = "Você ainda não tem nenhum contato.",
    connectionErrorTitle = "Ops! Parece que você não está conectado à internet.",
    serverErrorTitle = "Ops, algo de errado aconteceu aqui do nosso lado.",
    genericErrorTitle = "Ops. algo deu errado e não sabemos o que foi. Tente novamente.",
    okButtonText = "Ok, entendi.",
)
