package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.model.UserData

interface UserRepository {

    suspend fun getAllUsers(): Pair<List<UserData>, Long>
    suspend fun addCommentsToUser(user: UserData): UserData
}
