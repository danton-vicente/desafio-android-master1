package com.picpay.desafio.android.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.picpay.desafio.android.ui.AppTheme
import com.picpay.desafio.android.ui.components.GenericShimmer
import com.picpay.desafio.android.ui.components.GenericTopBar
import com.picpay.desafio.android.ui.components.UserPhotoWithInitials
import com.picpay.desafio.android.ui.dimens10
import com.picpay.desafio.android.ui.dimens12
import com.picpay.desafio.android.ui.dimens14
import com.picpay.desafio.android.ui.dimens15
import com.picpay.desafio.android.ui.dimens2
import com.picpay.desafio.android.ui.dimens20
import com.picpay.desafio.android.ui.dimens3
import com.picpay.desafio.android.ui.dimens4
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun ContactsScreen(
    state: ContactsScreenState,
    onEvent: (ContactsScreenEvents) -> Unit,
    navigationEvents: MutableSharedFlow<ContactsScreenNavigationEvents>,
    initViewModel: () -> Unit,
    onBackPressed: () -> Unit,
) {

    val localString = staticCompositionLocalOf { PtContactsScreenStrings }
    val string = localString.current

    LaunchedEffect(Unit) {
        initViewModel()
    }

    LaunchedEffect(navigationEvents) {
        navigationEvents.collect { event ->
            when (event) {
                is ContactsScreenNavigationEvents.NavigateBack -> {
                    onBackPressed()
                }
            }
        }
    }

    BackHandler {
        onEvent(ContactsScreenEvents.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            GenericTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = string.topbarTitle,
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background,
                )
                .padding(paddingValues),
        ) {
            if (state.isLoading) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimens2),
                    contentPadding = PaddingValues(dimens4),
                    content = {
                        items(10) {
                            GenericShimmer(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                cornerShape = dimens4,
                                height = dimens20,
                            )
                        }
                    }
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimens2,
                            horizontal = dimens4,
                        ),
                    text = string.lastUpdated(state.lastUpdateDate),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Thin
                    ),
                    textAlign = TextAlign.Center,
                )

                if (state.contacts.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = dimens2,
                                horizontal = dimens4,
                            ),
                        text = string.noContacts,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Thin
                        ),
                        textAlign = TextAlign.Center,
                    )
                }


                val listState = rememberLazyListState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimens2),
                    contentPadding = PaddingValues(dimens4),
                    content = {
                        items(state.contacts.size) { index ->
                            val user = state.contacts[index]
                            ContactsCard(
                                name = user.name,
                                userPhoto = user.img,
                                username = user.username,
                            )
                        }
                    }
                )
            }

            if (state.showError){

            }
        }
    }
}

@Composable
fun ContactsCard(
    name: String,
    userPhoto: String?,
    username: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimens20),
        shape = RoundedCornerShape(dimens4),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens3),
                verticalAlignment = Alignment.CenterVertically,
            ){
                UserPhotoWithInitials(
                    modifier = Modifier
                        .size(dimens14),
                    photoUrl = userPhoto,
                    userName = name,
                    initialStyle = MaterialTheme.typography.titleLarge
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                ) {

                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = dimens4
                            ),
                        text = username,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = dimens4
                            ),
                        text = name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        textAlign = TextAlign.Center,
                    )
                }

                IconButton(
                    modifier = Modifier
                        .size(dimens20)
                        .wrapContentSize(),
                    onClick = {  },
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
        }
    )
}

@Preview
@Composable
private fun ContactsScreenPreview() {
    AppTheme {
        ContactsScreen(
            state = ContactsScreenState(
                isLoading = false
            ),
            onEvent = {},
            navigationEvents = MutableSharedFlow(),
            initViewModel = {},
            onBackPressed = {}
        )
    }
}
