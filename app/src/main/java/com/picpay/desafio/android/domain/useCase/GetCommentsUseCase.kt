package com.picpay.desafio.android.domain.useCase

import com.picpay.desafio.android.commons.base.BaseUseCase
import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.repository.UserRepository

class GetCommentsUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Int, List<CommentsData>>() {

    override suspend fun execute(params: Int): List<CommentsData> {
        return userRepository
            .getCommentsById(params) ?: listOf()
    }
}
