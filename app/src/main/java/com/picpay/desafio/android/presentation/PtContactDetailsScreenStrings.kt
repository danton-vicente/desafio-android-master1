package com.picpay.desafio.android.presentation

val PtContactDetailsScreenStrings = ContactDetailsScreenStrings(
    copyWithSuccess = "Carteira copiada com sucesso!",
    addCommentTitle = "Adicionar comentário:",
    commentTitle = "Comentários:",
    addCommentButton = "Adicionar",
    addCommentError = "Ops, tivemos um erro ao adicionar um comentário :(",
    emptyComments = "Este usuário não tem comentários ainda. Que tal ser o primeiro?",
    errorOnLoadComments = "Ops, não conseguimos carregar os comentários no momento",
    likes = { likes ->
        when {
            likes == 1 -> "1 like"
            likes < 1_000 -> "$likes"
            likes < 1_000_000 -> "${likes / 1_000}.${(likes % 1_000) / 100}k"
            else -> "999k +"
        }
    },
    date = { date ->
        "Criado em: ${date
            .substring(8, 10)}/${date
                .substring(5, 7)}/${date
                    .substring(2, 4)} às ${date
                        .substring(11, 16)}"
    }
)
