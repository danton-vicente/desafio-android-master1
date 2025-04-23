package com.picpay.desafio.android.data_remote.dataSource

import com.picpay.desafio.android.data.remote.UserRemoteDataSource
import com.picpay.desafio.android.data_remote.mappers.toDomain
import com.picpay.desafio.android.data_remote.service.UserWebService
import com.picpay.desafio.android.data_remote.utils.Result.Error
import com.picpay.desafio.android.data_remote.utils.Result.Success
import com.picpay.desafio.android.data_remote.utils.executeCall
import com.picpay.desafio.android.domain.model.UserData

class UserRemoteDataSourceImpl(
    private val userWebService: UserWebService
) : UserRemoteDataSource {

    override suspend fun getUsers(): List<UserData> {
        return when (val result = executeCall { userWebService.getUsers() }) {
            is Success -> result.data.map { user ->
                user.toDomain()
            }

            is Error -> {
                throw result.cause ?: Exception(result.message)
            }
        }
    }

    override suspend fun addCommentsToUser(user: UserData): UserData {
        TODO("Not yet implemented")
    }
}
