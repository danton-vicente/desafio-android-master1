package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.domain.model.UserData

interface UserRemoteDataSource {

    suspend fun getUsers(): List<UserData>
    suspend fun addCommentsToUser(user: UserData): UserData

}
