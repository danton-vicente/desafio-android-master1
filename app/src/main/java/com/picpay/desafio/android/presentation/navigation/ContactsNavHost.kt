package com.picpay.desafio.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.picpay.desafio.android.presentation.ContactsScreen
import com.picpay.desafio.android.presentation.ContactsScreenState
import com.picpay.desafio.android.presentation.ContactsScreenViewModel
import com.picpay.desafio.android.ui.rememberFlowWithLifecycle
import com.picpay.desafio.android.presentation.routes.ContactsRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ContactsNavHost(
    navController: NavHostController,
    doOnBackPressed: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ContactsRoutes.ContactsScreenRoute.route,
    ) {
        navigateToContactScreen(
            navController = navController,
            doOnBackPressed = {
                doOnBackPressed()
            },
        )
    }
}

fun NavGraphBuilder.navigateToContactScreen(
    navController: NavHostController,
    doOnBackPressed: () -> Unit,
) {
    composable(route = ContactsRoutes.ContactsScreenRoute.route) {

        val contactsViewModel: ContactsScreenViewModel = koinViewModel()
        val state by rememberFlowWithLifecycle(contactsViewModel.uiState)
            .collectAsState(initial = ContactsScreenState())

        ContactsScreen(
            state = state,
            onEvent = { event ->
                contactsViewModel.onEvent(event)
            },
            navigationEvents = contactsViewModel.navigationEvents,
            initViewModel = {
                contactsViewModel.initViewModel()
            },
            onBackPressed = {
                doOnBackPressed()
            }
        )
    }
}
