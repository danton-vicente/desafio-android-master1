package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.commons.utils.NetworkChecker
import com.picpay.desafio.android.commons.utils.TimeProvider
import com.picpay.desafio.android.data.local.UserLocalDataSource
import com.picpay.desafio.android.data.remote.UserRemoteDataSource
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

    override suspend fun addCommentsToUser(user: UserData): UserData {
        TODO("Not yet implemented")
    }

    private suspend fun isDataRecentlyUpdated(): Boolean {
        val lastUpdate = userLocalDataSource.getLastUpdateDate()
        return timeProvider.getActualTime() - lastUpdate <= 5 * 60 * 1000
    }
}
