package com.picpay.desafio.android.domain.useCase

import com.picpay.desafio.android.commons.base.BaseUseCase
import com.picpay.desafio.android.domain.model.UserData
import com.picpay.desafio.android.domain.repository.UserRepository

class GetCompleteUserInfoUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Int, UserData?>() {

    override suspend fun execute(params: Int): UserData? {
        return userRepository
            .getCompleteInformationById(params)
    }
}
