package com.picpay.desafio.android.domain.useCase

import com.picpay.desafio.android.commons.base.BaseUseCase
import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.repository.UserRepository

class SetCommentsUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<CommentsData, CommentsData>() {

    override suspend fun execute(params: CommentsData): CommentsData {
        return userRepository
            .addCommentsToUser(
                commentsData = params
            )
    }
}
