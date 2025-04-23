package com.picpay.desafio.android.presentation.viewmodels

import com.picpay.desafio.android.di.testModule
import com.picpay.desafio.android.domain.model.UserData
import com.picpay.desafio.android.domain.useCase.GetUserUseCase
import com.picpay.desafio.android.presentation.events.ContactsScreenEvents
import com.picpay.desafio.android.presentation.events.ContactsScreenNavigationEvents
import com.picpay.desafio.android.presentation.states.ContactsScreenState
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContactsScreenViewModelTest {
    @Mock
    private lateinit var getContactsUseCase: GetUserUseCase

    private lateinit var viewModel: ContactsScreenViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        startKoin { modules(testModule) }

        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ContactsScreenViewModel(
            getUserUseCase = getContactsUseCase,
        )
        mockkObject(viewModel)
    }

    @Test
    fun `WHEN OnBackPressed event is collected THEN NavigateBack is emitted`() = runTest {
        // Arrange
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val screenActions = MutableSharedFlow<ContactsScreenEvents>()
        val navigationEvents = MutableSharedFlow<ContactsScreenNavigationEvents>()
        val viewModel = ContactsScreenViewModel(mockk()) // Use um mock para o use case

        val screenActionsField =
            ContactsScreenViewModel::class.java.getDeclaredField("screenActions")
        screenActionsField.isAccessible = true
        screenActionsField.set(viewModel, screenActions)

        val navigationEventsField =
            ContactsScreenViewModel::class.java.getDeclaredField("navigationEvents")
        navigationEventsField.isAccessible = true
        navigationEventsField.set(viewModel, navigationEvents)

        // Act
        viewModel.onEvent(ContactsScreenEvents.OnBackPressed)

        // Assert
        val emittedEvent = navigationEvents.first() // Aguarda o primeiro evento emitido
        Assert.assertEquals(ContactsScreenNavigationEvents.NavigateBack, emittedEvent)
    }

    @Test
    fun `WHEN OnCardClicked event is collected THEN NavigateToDetails With Correct Id is emitted`() =
        runTest {
            // Arrange
            val testDispatcher = StandardTestDispatcher(testScheduler)
            Dispatchers.setMain(testDispatcher)

            val screenActions = MutableSharedFlow<ContactsScreenEvents>()
            val navigationEvents = MutableSharedFlow<ContactsScreenNavigationEvents>()
            val viewModel = ContactsScreenViewModel(mockk()) // Use um mock para o use case
            // Use reflexão para acessar o campo privado _uiState
            val uiStateField = ContactsScreenViewModel::class.java.getDeclaredField("_uiState")
            uiStateField.isAccessible = true

            // Defina o valor desejado para o _uiState
            uiStateField.set(
                viewModel, MutableStateFlow(
                    ContactsScreenState(
                        isLoading = false,
                        showError = false,
                        lastUpdateDate = 0L,
                        contacts = listOf(
                            UserData(
                                id = 1,
                                img = "https://example.com/image.jpg",
                                name = "John Doe",
                                username = "johndoe",
                            ),
                            UserData(
                                id = 3,
                                img = "https://example.com/image2.jpg",
                                name = "Jane Smith",
                                username = "janesmith",
                            ),
                        )
                    )
                )
            )

            val screenActionsField =
                ContactsScreenViewModel::class.java.getDeclaredField("screenActions")
            screenActionsField.isAccessible = true
            screenActionsField.set(viewModel, screenActions)

            val navigationEventsField =
                ContactsScreenViewModel::class.java.getDeclaredField("navigationEvents")
            navigationEventsField.isAccessible = true
            navigationEventsField.set(viewModel, navigationEvents)

            // Act
            viewModel.onEvent(ContactsScreenEvents.OnCardClicked(1))

            // Assert
            val emittedEvent = navigationEvents.first() // Aguarda o primeiro evento emitido
            Assert.assertEquals(ContactsScreenNavigationEvents.NavigateToContactDetails(
                id = 3,
            ), emittedEvent)
        }

    @Test
    fun `WHEN OnDismissErrorBottomSheet event is collected THEN state is updated`() =
        runTest {
            // Arrange
            val testDispatcher = StandardTestDispatcher(testScheduler)
            Dispatchers.setMain(testDispatcher)

            val screenActions = MutableSharedFlow<ContactsScreenEvents>()
            val navigationEvents = MutableSharedFlow<ContactsScreenNavigationEvents>()
            val viewModel = ContactsScreenViewModel(mockk()) // Use um mock para o use case
            // Use reflexão para acessar o campo privado _uiState
            val uiStateField = ContactsScreenViewModel::class.java.getDeclaredField("_uiState")
            uiStateField.isAccessible = true

            // Defina o valor desejado para o _uiState
            uiStateField.set(
                viewModel, MutableStateFlow(
                    ContactsScreenState(
                        isLoading = false,
                        showError = true,
                        lastUpdateDate = 0L,
                        contacts = listOf()
                    )
                )
            )

            val screenActionsField =
                ContactsScreenViewModel::class.java.getDeclaredField("screenActions")
            screenActionsField.isAccessible = true
            screenActionsField.set(viewModel, screenActions)

            val navigationEventsField =
                ContactsScreenViewModel::class.java.getDeclaredField("navigationEvents")
            navigationEventsField.isAccessible = true
            navigationEventsField.set(viewModel, navigationEvents)

            // Act
            viewModel.onEvent(ContactsScreenEvents.OnDismissErrorBottomSheet)

            // Assert
            val updatedState = viewModel.uiState.first { it.showError == false }
            Assert.assertEquals(false, updatedState.showError)
        }
}
