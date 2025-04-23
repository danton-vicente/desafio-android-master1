package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.commons.utils.NetworkChecker
import com.picpay.desafio.android.commons.utils.TimeProvider
import com.picpay.desafio.android.data.local.UserLocalDataSource
import com.picpay.desafio.android.data.remote.UserRemoteDataSource
import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val timeProvider: TimeProvider,
    private val networkChecker: NetworkChecker,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    override suspend fun getAllUsers(): Pair<List<UserData>, Long> {
        if (!networkChecker.hasNetworkConnection() || isDataRecentlyUpdated()) {
            return userLocalDataSource.getAllUsers()
        }

        val users = userRemoteDataSource.getUsers()
        val timestamp = timeProvider.getActualTime()
        userLocalDataSource.insertUsers(users)
        return Pair(users, timestamp)
    }

    override suspend fun getCompleteInformationById(id: Int): UserData? {
        if (!networkChecker.hasNetworkConnection()) {
            return userLocalDataSource.getUserById(id)
        }

        val user = userRemoteDataSource.getCompleteInformationById(id)

        user?.let {
            userLocalDataSource.insertUser(user)
        }
        return user
    }

    override suspend fun getCommentsById(id: Int): List<CommentsData>? {
        if (!networkChecker.hasNetworkConnection()) {
            return userLocalDataSource.getCommentsById(id)
        }

        val comments = userRemoteDataSource.getCommentsById(id)
        comments?.let {
            userLocalDataSource.insertComments(it)
        }
        return comments
    }

    override suspend fun addCommentsToUser(commentsData: CommentsData): CommentsData {
        val response = userRemoteDataSource.addCommentsToUser(
            commentsData = commentsData
        )
        userLocalDataSource.insertComments(listOf(response))
        return response
    }


    private suspend fun isDataRecentlyUpdated(): Boolean {
        val lastUpdate = userLocalDataSource.getLastUpdateDate()
        return timeProvider.getActualTime() - lastUpdate <= 5 * 60 * 1000
    }
}
