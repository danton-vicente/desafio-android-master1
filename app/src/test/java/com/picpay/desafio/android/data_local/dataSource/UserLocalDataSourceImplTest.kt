package com.picpay.desafio.android.data_local.dataSource

import com.picpay.desafio.android.commons.utils.TimeProvider
import com.picpay.desafio.android.data_local.dao.UserDAO
import com.picpay.desafio.android.data_local.model.UserEntity
import com.picpay.desafio.android.di.testModule
import com.picpay.desafio.android.domain.model.UserData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class UserLocalDataSourceImplTest {

    @Mock
    private lateinit var timeProvider: TimeProvider

    @Mock
    private lateinit var userDao: UserDAO

    private lateinit var subject: UserLocalDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        startKoin { modules(testModule) }
        subject = UserLocalDataSourceImpl(
            timeProvider = timeProvider,
            userDao = userDao
        )
    }

    @After
    fun reset() {
        stopKoin()
    }

    @Test
    fun `getAllUsers empty database`() {
        // Arrange
        val expected = Pair(listOf<UserData>(), FAKE_TIMESTAMP - 1 * 60 * 1000)

        whenever(
            timeProvider.getActualTime()
        ).thenReturn(
            FAKE_TIMESTAMP
        )

        whenever(
            userDao.getAllUsers()
        ).thenReturn(
            listOf()
        )

        runBlocking {
            whenever(
                userDao.getMostRecentUpdate()
            ).thenReturn(
                FAKE_TIMESTAMP - 1 * 60 * 1000
            )
        }

        // Act
        var result: Pair<List<UserData>, Long>? = null
        runBlocking {
            result = subject.getAllUsers()
        }

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun `getAllUsers non empty database`() {
        // Arrange
        val expected = Pair(expectedResult(), FAKE_TIMESTAMP - 1 * 60 * 1000)

        whenever(
            timeProvider.getActualTime()
        ).thenReturn(
            FAKE_TIMESTAMP
        )

        whenever(
            userDao.getAllUsers()
        ).thenReturn(
            getMockedEntity()
        )

        runBlocking {
            whenever(
                userDao.getMostRecentUpdate()
            ).thenReturn(
                FAKE_TIMESTAMP - 1 * 60 * 1000
            )
        }

        // Act
        var result: Pair<List<UserData>, Long>? = null
        runBlocking {
            result = subject.getAllUsers()
        }

        // Assert
        assertEquals(expected, result)
    }

    private fun getMockedEntity(): List<UserEntity> {
        return listOf(
            UserEntity(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                img = "https://example.com/johndoe.jpg",
                comments = listOf("Hello World"),
                lastUpdated = FAKE_TIMESTAMP - 120 * 1000,
            ),
            UserEntity(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                img = "https://example.com/janesmith.jpg",
                comments = listOf("Testing comments","this is a test","this is a test"),
                lastUpdated = FAKE_TIMESTAMP - 120 * 1000,
            ),
            UserEntity(
                id = 3,
                name = "Alice Johnson",
                username = "alicej",
                img = "https://example.com/alicej.jpg",
                comments = listOf(),
                lastUpdated = FAKE_TIMESTAMP - 120 * 1000,
            ),
            UserEntity(
                id = 4,
                name = "Bob Brown",
                username = "bobbrown",
                img = "https://example.com/bobbrown.jpg",
                comments = listOf(),
                lastUpdated = FAKE_TIMESTAMP - 60 * 1000,
            )
        )
    }

    private fun expectedResult(): List<UserData> {
        return listOf(
            UserData(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                img = "https://example.com/johndoe.jpg",
                comments = listOf("Hello World"),
            ),
            UserData(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                img = "https://example.com/janesmith.jpg",
                comments = listOf("Testing comments","this is a test","this is a test"),
            ),
            UserData(
                id = 3,
                name = "Alice Johnson",
                username = "alicej",
                img = "https://example.com/alicej.jpg",
                comments = listOf(),
            ),
            UserData(
                id = 4,
                name = "Bob Brown",
                username = "bobbrown",
                img = "https://example.com/bobbrown.jpg",
                comments = listOf(),
            )
        )
    }

    companion object {
        private const val FAKE_TIMESTAMP = 1745385376319
    }
}
