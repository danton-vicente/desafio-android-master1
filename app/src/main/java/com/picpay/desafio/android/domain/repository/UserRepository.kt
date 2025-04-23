package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData

interface UserRepository {

    suspend fun getAllUsers(): Pair<List<UserData>, Long>
    suspend fun getCompleteInformationById(id: Int): UserData?
    suspend fun getCommentsById(id: Int): List<CommentsData>?
    suspend fun addCommentsToUser(commentsData: CommentsData): CommentsData
}
