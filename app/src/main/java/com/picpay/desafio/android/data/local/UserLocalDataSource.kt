package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData

interface UserLocalDataSource {

    suspend fun getAllUsers(): Pair<List<UserData>, Long>
    suspend fun getUserById(id: Int): UserData?
    suspend fun getLastUpdateDate(): Long
    suspend fun insertUser(user: UserData)
    suspend fun insertUsers(users: List<UserData>)
    suspend fun getCommentsById(id: Int): List<CommentsData>?
    suspend fun insertComments(comments: List<CommentsData>)

}
