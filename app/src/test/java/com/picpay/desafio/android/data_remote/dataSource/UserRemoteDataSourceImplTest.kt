package com.picpay.desafio.android.data_remote.dataSource

import com.picpay.desafio.android.data_remote.model.UserResponse
import com.picpay.desafio.android.data_remote.service.UserWebService
import com.picpay.desafio.android.di.testModule
import com.picpay.desafio.android.domain.model.UserData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Call
import retrofit2.Response
import kotlin.test.assertEquals


class UserRemoteDataSourceImplTest {

    @Mock
    private lateinit var userWebService: UserWebService

    private lateinit var subject: UserRemoteDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        startKoin { modules(testModule) }
        subject = UserRemoteDataSourceImpl(
            userWebService = userWebService,
        )
    }

    @After
    fun reset() {
        stopKoin()
    }

    @Test
    fun `getUsers success empty list`() {
        // Arrange

        val mockedResponse = Response.success(listOf<UserResponse>())
        val expectedResult = listOf<UserData>()
        val call = mock<Call<List<UserResponse>>>()

        whenever(
            call.execute()
        ).thenReturn(
            mockedResponse
        )

        whenever(
            userWebService.getUsers()
        ).thenReturn(
            call
        )

        // Act
        var result: List<UserData>?
        runBlocking {
            result = subject.getUsers()
        }

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getUsers success single user`() {
        // Arrange

        val mockedResponse = Response.success(listOf(
            UserResponse(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                img = "https://example",
                comments = null,
        )))

        val expectedResult = listOf(
            UserData(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                img = "https://example",
                comments = listOf(),
            )
        )
        val call = mock<Call<List<UserResponse>>>()

        whenever(
            call.execute()
        ).thenReturn(
            mockedResponse
        )

        whenever(
            userWebService.getUsers()
        ).thenReturn(
            call
        )

        // Act
        var result: List<UserData>?
        runBlocking {
            result = subject.getUsers()
        }

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getUsers success multiple users`() {
        // Arrange

        val mockedResponse = Response.success(getMockedResponse())
        val expectedResult = expectedResult()
        val call = mock<Call<List<UserResponse>>>()

        whenever(
            call.execute()
        ).thenReturn(
            mockedResponse
        )

        whenever(
            userWebService.getUsers()
        ).thenReturn(
            call
        )

        // Act
        var result: List<UserData>?
        runBlocking {
            result = subject.getUsers()
        }

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getUsers web service network error`() {
        // Arrange

        val mockedException = RuntimeException("Network error")
        val call = mock<Call<List<UserResponse>>>()

        whenever(
            call.execute()
        ).thenThrow(
            mockedException
        )

        whenever(
            userWebService.getUsers()
        ).thenReturn(
            call
        )

        // Act & Assert
        val exception = kotlin.test.assertFailsWith<RuntimeException> {
            runBlocking {
                subject.getUsers()
            }
        }

        assertEquals("Network error", exception.message)
    }

    private fun getMockedResponse(): List<UserResponse> {
        return listOf(
            UserResponse(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                img = "https://example.com/johndoe.jpg",
                comments = listOf("Hello World"),
            ),
            UserResponse(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                img = "https://example.com/janesmith.jpg",
                comments = listOf("Testing comments"),
            ),
            UserResponse(
                id = 3,
                name = "Alice Johnson",
                username = "alicej",
                img = "https://example.com/alicej.jpg",
                comments = null,
            ),
            UserResponse(
                id = 4,
                name = "Bob Brown",
                username = "bobbrown",
                img = "https://example.com/bobbrown.jpg",
                comments = listOf(),
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

}
