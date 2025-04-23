package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData

interface UserRemoteDataSource {

    suspend fun getUsers(): List<UserData>
    suspend fun getCompleteInformationById(id: Int): UserData?
    suspend fun getCommentsById(id: Int): List<CommentsData>?
    suspend fun addCommentsToUser(commentsData: CommentsData): CommentsData
}
