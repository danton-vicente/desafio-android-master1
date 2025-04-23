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

    @Test
    fun `getAllUsers database error`() {
        // Simulate a database error when calling getAllUsers and verify that an appropriate exception is thrown.
        // TODO implement test
    }

    @Test
    fun `getAllUsers mapping error`() {
        // Test that if there is an issue mapping the database entity to a UserData object, an appropriate exception is thrown.
        // TODO implement test
    }

    @Test
    fun `getAllUsers recent update error`() {
        // Simulate a database error when getting the recent update timestamp 
        // when calling getAllUsers and verify that an appropriate exception is thrown.
        // TODO implement test
    }

    @Test
    fun `insertUsers valid users`() {

    }

    @Test
    fun `insertUsers empty user list`() {
        // Test that insertUsers handles an empty list of users without errors.
        // TODO implement test
    }

    @Test
    fun `insertUsers database error`() {
        // Simulate a database error during insertion and verify that an appropriate exception is thrown.
        // TODO implement test
    }

    @Test
    fun `insertUsers mapping error`() {
        // Test that if there is an issue mapping the UserData object to a database entity, 
        // an appropriate exception is thrown.
        // TODO implement test
    }

    @Test
    fun `getLastUpdateDate valid date`() {
        // Test that getLastUpdateDate returns the most recent update timestamp from the database.
        // TODO implement test
    }

    @Test
    fun `getLastUpdateDate empty database`() {
        // Test that when the database has no update entries, getLastUpdateDate returns a default value (e.g., 0).
        // TODO implement test
    }

    private fun getMockedEntity(): List<UserEntity> {
        return listOf(
            UserEntity(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                img = "https://example.com/johndoe.jpg",
                comments = "",
                lastUpdated = FAKE_TIMESTAMP - 120 * 1000,
            ),
            UserEntity(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                img = "https://example.com/janesmith.jpg",
                comments = "[Testing comments,this is a test,this is a test]",
                lastUpdated = FAKE_TIMESTAMP - 120 * 1000,
            ),
            UserEntity(
                id = 3,
                name = "Alice Johnson",
                username = "alicej",
                img = "https://example.com/alicej.jpg",
                comments = "",
                lastUpdated = FAKE_TIMESTAMP - 120 * 1000,
            ),
            UserEntity(
                id = 4,
                name = "Bob Brown",
                username = "bobbrown",
                img = "https://example.com/bobbrown.jpg",
                comments = "",
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
