package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.commons.utils.NetworkChecker
import com.picpay.desafio.android.commons.utils.TimeProvider
import com.picpay.desafio.android.data.local.UserLocalDataSource
import com.picpay.desafio.android.data.remote.UserRemoteDataSource
import com.picpay.desafio.android.data_local.model.UserEntity
import com.picpay.desafio.android.data_remote.model.UserResponse
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class UserRepositoryImplTest {

    @Mock
    private lateinit var userLocalDataSource: UserLocalDataSource

    @Mock
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @Mock
    private lateinit var timeProvider: TimeProvider

    private lateinit var subject: UserRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        startKoin { modules(testModule) }
        subject = UserRepositoryImpl(
            timeProvider = timeProvider,
            networkChecker = networkChecker,
            userRemoteDataSource = userRemoteDataSource,
            userLocalDataSource = userLocalDataSource
        )
    }

    @After
    fun reset() {
        stopKoin()
    }

    @Test
    fun `getAllUsers network available and data not recently updated`() {
        // Arrange
        val expectedResult = Pair(expectedResult(), FAKE_TIMESTAMP)

        whenever(
            networkChecker.hasNetworkConnection()
        ).thenReturn(
            true
        )

        whenever(
            timeProvider.getActualTime()
        ).thenReturn(
            FAKE_TIMESTAMP
        )

        runBlocking {
            whenever(
                userLocalDataSource.getLastUpdateDate()
            ).thenReturn(
                FAKE_TIMESTAMP - 6 * 60 * 1000
            )
        }

        runBlocking {
            whenever(
                userRemoteDataSource.getUsers()
            ).thenReturn(
                expectedResult()
            )
        }

        // Act
        var result: Pair<List<UserData>, Long>

        runBlocking {
            result = subject.getAllUsers()
        }

        // Assert
        runBlocking {
            verify(userLocalDataSource, times(0)).getAllUsers()
        }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAllUsers network unavailable`() {
        // Arrange
        val expectedResult = Pair(expectedResult(), FAKE_TIMESTAMP - 10 * 60 * 1000)

        whenever(
            networkChecker.hasNetworkConnection()
        ).thenReturn(
            false
        )

        whenever(
            timeProvider.getActualTime()
        ).thenReturn(
            FAKE_TIMESTAMP
        )

        runBlocking {
            whenever(
                userLocalDataSource.getLastUpdateDate()
            ).thenReturn(
                FAKE_TIMESTAMP - 6 * 60 * 1000
            )
        }

        runBlocking {
            whenever(
                userLocalDataSource.getAllUsers()
            ).thenReturn(
                Pair(expectedResult(), FAKE_TIMESTAMP - 10 * 60 * 1000)
            )
        }

        // Act
        var result: Pair<List<UserData>, Long>

        runBlocking {
            result = subject.getAllUsers()
        }

        // Assert
        runBlocking {
            verify(userRemoteDataSource, times(0)).getUsers()
        }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAllUsers data recently updated`() {
        // Arrange
        val expectedResult = Pair(expectedResult(), FAKE_TIMESTAMP - 1 * 60 * 1000)

        whenever(
            networkChecker.hasNetworkConnection()
        ).thenReturn(
            true
        )

        whenever(
            timeProvider.getActualTime()
        ).thenReturn(
            FAKE_TIMESTAMP
        )

        runBlocking {
            whenever(
                userLocalDataSource.getLastUpdateDate()
            ).thenReturn(
                FAKE_TIMESTAMP - 1 * 60 * 1000
            )
        }

        runBlocking {
            whenever(
                userLocalDataSource.getAllUsers()
            ).thenReturn(
                Pair(expectedResult(), FAKE_TIMESTAMP - 1 * 60 * 1000)
            )
        }

        // Act
        var result: Pair<List<UserData>, Long>

        runBlocking {
            result = subject.getAllUsers()
        }

        // Assert
        runBlocking {
            verify(userRemoteDataSource, times(0)).getUsers()
        }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAllUsers empty remote data`() {
        // Arrange
        val expectedResult = Pair(listOf<UserData>(), FAKE_TIMESTAMP)

        whenever(
            networkChecker.hasNetworkConnection()
        ).thenReturn(
            true
        )

        whenever(
            timeProvider.getActualTime()
        ).thenReturn(
            FAKE_TIMESTAMP
        )

        runBlocking {
            whenever(
                userLocalDataSource.getLastUpdateDate()
            ).thenReturn(
                FAKE_TIMESTAMP - 6 * 60 * 1000
            )
        }

        runBlocking {
            whenever(
                userRemoteDataSource.getUsers()
            ).thenReturn(
                listOf()
            )
        }

        // Act
        var result: Pair<List<UserData>, Long>

        runBlocking {
            result = subject.getAllUsers()
        }

        // Assert
        runBlocking {
            verify(userLocalDataSource, times(0)).getAllUsers()
        }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAllUsers empty local data`() {
        // Test when local data source returns an empty list, it should return an empty list and timestamp.
        // TODO implement test
    }

    @Test
    fun `getAllUsers large dataset from remote`() {
        // Test when remote data source returns a very large dataset, check if the insert into local works correctly 
        // and no memory issues.
        // TODO implement test
    }

    @Test
    fun `getAllUsers timestamp edge case   exactly 5 mins`() {
        // Test when data was last updated exactly 5 minutes ago, it should return the local data.
        // TODO implement test
    }

    @Test
    fun `getAllUsers timestamp edge case   just over 5 mins`() {
        // Test when data was last updated just over 5 minutes ago, it should fetch from 
        // remote and update local.
        // TODO implement test
    }

    @Test
    fun `addCommentsToUser success`() {
        // Test when comments are successfully added to the user. 
        // Note this function has a TODO meaning it hasn't been implemented, but 
        // will have some functionality later on.
        // TODO implement test
    }

    @Test
    fun `addCommentsToUser null input user`() {
        // Test with null input user, should function throw an error? if so what error?.
        // TODO implement test
    }

    @Test
    fun `addCommentsToUser empty comments string`() {
        // Test with an empty string for comments, should function throw an error?.
        // TODO implement test
    }

    @Test
    fun `addCommentsToUser very large comment string`() {
        // Test with an very large string for comments, does the function have any limitation 
        // on the length of input string.
        // TODO implement test
    }

    @Test
    fun `addCommentsToUser invalid characters`() {
        // Test with special or invalid characters in the comment string, what error is thrown?
        // TODO implement test
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
                comments = listOf("Testing comments"),
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
