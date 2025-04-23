package com.picpay.desafio.android.presentation.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.useCase.GetUserUseCase
import com.picpay.desafio.android.presentation.events.ContactsScreenEvents
import com.picpay.desafio.android.presentation.events.ContactsScreenNavigationEvents
import com.picpay.desafio.android.presentation.states.ContactsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactsScreenViewModel(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ContactsScreenState> =
        MutableStateFlow(ContactsScreenState())

    val uiState: StateFlow<ContactsScreenState> = _uiState

    private val screenActions = MutableSharedFlow<ContactsScreenEvents>()
    val navigationEvents = MutableSharedFlow<ContactsScreenNavigationEvents>()

    init {
        handleScreenEvents()
    }

    fun initViewModel() {

        viewModelScope.launch(Dispatchers.IO) {
            updateUiState(
                _uiState.value.copy(
                    isLoading = true,
                    showError = false,
                )
            )

            getUserUseCase(
                params = Unit,
                onSuccess = { users ->
                    updateUiState(
                        _uiState.value.copy(
                            isLoading = false,
                            contacts = users.first,
                            lastUpdateDate = users.second
                        )
                    )
                },
                onError = { error ->
                    updateUiState(
                        _uiState.value.copy(
                            isLoading = false,
                            showError = true,
                        )
                    )
                }
            )
        }
    }

    private fun handleScreenEvents() {
        viewModelScope.launch {
            screenActions.collect { event ->
                when (event) {
                    ContactsScreenEvents.OnBackPressed -> {
                        emitNavigationEvents(ContactsScreenNavigationEvents.NavigateBack)
                    }
                    is ContactsScreenEvents.OnCardClicked -> {
                        val selectedUser = _uiState.value.contacts[event.index]
                        emitNavigationEvents(
                            ContactsScreenNavigationEvents.NavigateToContactDetails(
                                selectedUser.id
                            )
                        )
                    }
                    ContactsScreenEvents.OnDismissErrorBottomSheet -> {
                        updateUiState(
                            _uiState.value.copy(
                                showError = false
                            )
                        )
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun emitNavigationEvents(event: ContactsScreenNavigationEvents) {
        viewModelScope.launch {
            navigationEvents.emit(event)
        }
    }

    fun onEvent(event: ContactsScreenEvents) {
        viewModelScope.launch {
            screenActions.emit(event)
        }
    }

    private fun updateUiState(newState: ContactsScreenState) {
        _uiState.value = newState
    }

}
