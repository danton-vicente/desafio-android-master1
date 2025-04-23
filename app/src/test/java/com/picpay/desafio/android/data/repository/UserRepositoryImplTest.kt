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
    
    private fun expectedResult(): List<UserData> {
        return listOf(
            UserData(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                img = "https://example.com/johndoe.jpg",
                bitcoinWallet = "1sgQxKtQBtHc1wvEw42s9hcnz8VF9ee6",
                ethereumWallet = "0x3103a5b4a0b1c51d2de4b8ebd13a3cfefdcd43aa",
                litecoinWallet = "3tzpUeALpoPcpJFZ6CH3k37JtH7WS9eYY",
            ),
            UserData(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                img = "https://example.com/janesmith.jpg",
                bitcoinWallet = "1sgQxKtQBtHc1w123vs9hcnz8VF9ee6",
                ethereumWallet = "0x3103a5b4a0b1c51dbasf2de4b8ebd13a3cfefdcd43aa",
                litecoinWallet = "3tzpUeALpoPcpJFZ612317JtH7WS9eYY",
            ),
            UserData(
                id = 3,
                name = "Alice Johnson",
                username = "alicej",
                img = "https://example.com/alicej.jpg",
                bitcoinWallet = "1sgQxKtQBtHc1wvEw4dfdf2s9hcnz8VF9ee6",
                ethereumWallet = "0x3103a5b4a0b1c5123ffasebd13a3cfefdcd43aa",
                litecoinWallet = "3tzpUeALpoPc123412fhbjh3k37JtH7WS9eYY",
            ),
            UserData(
                id = 4,
                name = "Bob Brown",
                username = "bobbrown",
                img = "https://example.com/bobbrown.jpg",
                bitcoinWallet = "1sgQxKtQBtHc123123cnz8VF9ee6",
                ethereumWallet = "0x3103a5b4a0b1c5sdgasdgf8ebd13a3cfefdcd43aa",
                litecoinWallet = "3tzpUeALpoPcpJ3233ftH7WS9eYY",
            )
        )
    }

    companion object {
        private const val FAKE_TIMESTAMP = 1745385376319
    }
}
