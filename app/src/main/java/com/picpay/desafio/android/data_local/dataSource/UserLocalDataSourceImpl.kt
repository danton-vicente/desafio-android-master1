package com.picpay.desafio.android.data_local.dataSource

import com.picpay.desafio.android.commons.utils.TimeProvider
import com.picpay.desafio.android.data.local.UserLocalDataSource
import com.picpay.desafio.android.data_local.dao.CommentsDAO
import com.picpay.desafio.android.data_local.dao.UserDAO
import com.picpay.desafio.android.data_local.mappers.toDomain
import com.picpay.desafio.android.data_local.mappers.toEntity
import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData

class UserLocalDataSourceImpl(
    private val timeProvider: TimeProvider,
    private val userDao: UserDAO,
    private val commentsDAO: CommentsDAO
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

    override suspend fun getCommentsById(id: Int): List<CommentsData>? {
        return commentsDAO.getAllCommentsFromUserId(id).map {
            it.toDomain()
        }
    }

    override suspend fun getLastUpdateDate(): Long =
        userDao.getMostRecentUpdate()

    override suspend fun getUserById(id: Int): UserData? {
        return userDao.getUserById(id)?.toDomain()
    }

    override suspend fun insertUser(user: UserData) {
        userDao.insert(
            user.toEntity(
                timeProvider.getActualTime()
            )
        )
    }

    override suspend fun insertComments(comments: List<CommentsData>) {
        commentsDAO.insertAll(
            comments.map {
                it.toEntity()
            }
        )
    }
}
