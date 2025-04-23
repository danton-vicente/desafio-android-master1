package com.picpay.desafio.android.domain.useCase

import com.picpay.desafio.android.commons.base.BaseUseCase
import com.picpay.desafio.android.domain.model.UserData
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Pair<List<UserData>, Long>>() {

    override suspend fun execute(params: Unit): Pair<List<UserData>, Long> {
        return userRepository.getAllUsers()
    }
}
