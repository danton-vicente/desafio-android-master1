package com.picpay.desafio.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.useCase.GetCommentsUseCase
import com.picpay.desafio.android.domain.useCase.GetCompleteUserInfoUseCase
import com.picpay.desafio.android.domain.useCase.SetCommentsUseCase
import com.picpay.desafio.android.presentation.events.ContactsScreenNavigationEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactDetailsScreenViewModel(
    private val getCompleteUserInfoUseCase: GetCompleteUserInfoUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val setCommentsUseCase: SetCommentsUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ContactDetailsScreenState> =
        MutableStateFlow(ContactDetailsScreenState())

    val uiState: StateFlow<ContactDetailsScreenState> = _uiState

    private val screenActions = MutableSharedFlow<ContactDetailsScreenEvents>()
    val navigationEvents = MutableSharedFlow<ContactDetailsScreenNavigationEvents>()

    init {
        handleScreenEvents()
    }

    fun initViewModel(userId: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            getCompleteUserInfoUseCase(
                params = userId,
                onSuccess = { user ->
                    if (user != null) {
                        updateUiState(
                            _uiState.value.copy(
                                userData = user,
                                isLoadingUserData = false
                            )
                        )
                    }
                },
                onError = { error ->
                    updateUiState(
                        _uiState.value.copy(
                            isLoadingUserData = false
                        )
                    )
                }
            )
        }

        viewModelScope.launch(Dispatchers.IO) {

            getCommentsUseCase(
                params = userId,
                onSuccess = { comments ->
                    updateUiState(
                        _uiState.value.copy(
                            comments = comments,
                            showLoadingComments = false
                        )
                    )
                },
                onError = { error ->
                    updateUiState(
                        _uiState.value.copy(
                            showLoadingComments = false
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
                    ContactDetailsScreenEvents.OnBackPressed -> {
                        emitNavigationEvents(ContactDetailsScreenNavigationEvents.NavigateBack)
                    }
                    else -> {
                        // Handle events here
                    }
                }
            }
        }
    }

    private fun emitNavigationEvents(event: ContactDetailsScreenNavigationEvents) {
        viewModelScope.launch {
            navigationEvents.emit(event)
        }
    }

    fun onEvent(event: ContactDetailsScreenEvents) {
        viewModelScope.launch {
            screenActions.emit(event)
        }
    }

    private fun updateUiState(newState: ContactDetailsScreenState) {
        _uiState.value = newState
    }
}
