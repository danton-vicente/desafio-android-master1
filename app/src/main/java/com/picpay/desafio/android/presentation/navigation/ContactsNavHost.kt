package com.picpay.desafio.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.picpay.desafio.android.presentation.ContactDetailsScreen
import com.picpay.desafio.android.presentation.ContactDetailsScreenState
import com.picpay.desafio.android.presentation.ContactDetailsScreenViewModel
import com.picpay.desafio.android.presentation.screens.ContactsScreen
import com.picpay.desafio.android.presentation.states.ContactsScreenState
import com.picpay.desafio.android.presentation.viewmodels.ContactsScreenViewModel
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

        navigateToContactDetailScreen(
            doOnBackPressed = {
                doOnBackPressed()
            }
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
            },
            onNavigateToDetails = { userId ->
                navController.navigate(ContactsRoutes.ContactsDetailsScreenRoute.route + "/$userId")
            }
        )
    }
}

fun NavGraphBuilder.navigateToContactDetailScreen(
    doOnBackPressed: () -> Unit,
) {
    composable(route = ContactsRoutes.ContactsDetailsScreenRoute.route + "/{userId}") { backStackEntry ->
        val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: return@composable

        val contactDetailsViewmodel: ContactDetailsScreenViewModel = koinViewModel()
        val state by rememberFlowWithLifecycle(contactDetailsViewmodel.uiState)
            .collectAsState(initial = ContactDetailsScreenState())

        ContactDetailsScreen(
            state = state,
            onEvent = { event ->
                contactDetailsViewmodel.onEvent(event)
            },
            navigationEvents = contactDetailsViewmodel.navigationEvents,
            initViewModel = {
                contactDetailsViewmodel.initViewModel(userId)
            },
            onBackPressed = {
                doOnBackPressed()
            },
        )
    }
}


