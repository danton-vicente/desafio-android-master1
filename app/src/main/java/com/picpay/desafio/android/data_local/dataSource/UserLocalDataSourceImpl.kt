package com.picpay.desafio.android.data_local.dataSource

import com.picpay.desafio.android.commons.utils.TimeProvider
import com.picpay.desafio.android.data.local.UserLocalDataSource
import com.picpay.desafio.android.data_local.dao.UserDAO
import com.picpay.desafio.android.data_local.mappers.toDomain
import com.picpay.desafio.android.data_local.mappers.toEntity
import com.picpay.desafio.android.domain.model.UserData

class UserLocalDataSourceImpl(
    private val timeProvider: TimeProvider,
    private val userDao: UserDAO
) : UserLocalDataSource {

    override suspend fun getAllUsers(): Pair<List<UserData>, Long> {
        return Pair(userDao.getAllUsers().map {
            it.toDomain()
        }, userDao.getMostRecentUpdate())
    }

    override suspend fun insertUsers(users: List<UserData>) {
        userDao.insertAll(users.map {
            it.toEntity(timeProvider.getActualTime())
        })
    }

    override suspend fun getLastUpdateDate(): Long =
        userDao.getMostRecentUpdate()

    override suspend fun getUserById(id: Int): UserData? {
        TODO("Not yet implemented")
    }

    override suspend fun insertUser(user: UserData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserById(id: Int) {
        TODO("Not yet implemented")
    }

}
