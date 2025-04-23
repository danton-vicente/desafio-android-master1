package com.picpay.desafio.android.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData
import com.picpay.desafio.android.presentation.events.ContactsScreenEvents
import com.picpay.desafio.android.presentation.screens.ContactsCard
import com.picpay.desafio.android.ui.AppTheme
import com.picpay.desafio.android.ui.components.GenericShimmer
import com.picpay.desafio.android.ui.components.GenericTopBar
import com.picpay.desafio.android.ui.components.UserPhotoWithInitials
import com.picpay.desafio.android.ui.dimens1
import com.picpay.desafio.android.ui.dimens10
import com.picpay.desafio.android.ui.dimens2
import com.picpay.desafio.android.ui.dimens20
import com.picpay.desafio.android.ui.dimens3
import com.picpay.desafio.android.ui.dimens4
import com.picpay.desafio.android.ui.dimens6
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    state: ContactDetailsScreenState,
    onEvent: (ContactDetailsScreenEvents) -> Unit,
    navigationEvents: MutableSharedFlow<ContactDetailsScreenNavigationEvents>,
    initViewModel: () -> Unit,
    onBackPressed: () -> Unit,
) {

    val localString = staticCompositionLocalOf { PtContactDetailsScreenStrings }
    val string = localString.current

    LaunchedEffect(Unit) {
        initViewModel()
    }

    LaunchedEffect(navigationEvents) {
        navigationEvents.collect { event ->
            when (event) {
                is ContactDetailsScreenNavigationEvents.NavigateBack -> {
                    onBackPressed()
                }
            }
        }
    }

    BackHandler {
        onEvent(ContactDetailsScreenEvents.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            GenericTopBar(
                title = null,
                startIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onStartIconClick = {
                    onEvent(ContactDetailsScreenEvents.OnBackPressed)
                },
            )
        },
        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    onEvent(ContactDetailsScreenEvents.OnAddCommentClicked)
//                },
//                containerColor = MaterialTheme.colorScheme.primary
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = "Adicionar",
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background,
                )
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(state.isLoadingUserData) {
                GenericShimmer(
                    modifier = Modifier
                        .width(120.dp)
                        .clip(CircleShape),
                    cornerShape = 120.dp,
                    height = 120.dp,
                )

                GenericShimmer(
                    modifier = Modifier
                        .width(240.dp)
                        .padding(
                            top = dimens4
                        ),
                    cornerShape = dimens4,
                    height = dimens10,
                )

                GenericShimmer(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(
                            top = dimens2
                        ),
                    cornerShape = dimens4,
                    height = dimens6,
                )

                GenericShimmer(
                    modifier = Modifier
                        .padding(
                            top = dimens4,
                            start = dimens4,
                            end = dimens4,
                        ),
                    cornerShape = dimens4,
                    height = 180.dp,
                )

            } else {
                UserPhotoWithInitials(
                    modifier = Modifier
                        .size(120.dp),
                    photoUrl = state.userData.img,
                    userName = state.userData.name,
                    initialStyle = MaterialTheme.typography.displayLarge,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimens4
                        ),
                    text = state.userData.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimens2
                        ),
                    text = "@${state.userData.username}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Thin
                    ),
                    textAlign = TextAlign.Center,
                )

                CustomOutlinedContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimens4,
                        ),
                    title = "Wallets",
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimens2)
                        ) {
                            val wallets = listOf(
                                Triple(
                                    Icons.Default.AccountCircle, state.userData.bitcoinWallet,
                                    ContactDetailsWallets.Bitcoin
                                ),
                                Triple(
                                    Icons.Default.AccountCircle, state.userData.ethereumWallet,
                                    ContactDetailsWallets.Ethereum
                                ),
                                Triple(
                                    Icons.Default.AccountCircle, state.userData.litecoinWallet,
                                    ContactDetailsWallets.Litecoin
                                )
                            )

                            wallets.forEach { (icon, walletKey, walletType) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = dimens1),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = "$walletType Wallet",
                                        modifier = Modifier.size(24.dp)
                                    )

                                    Text(
                                        text = walletKey,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = dimens2),
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Start
                                    )

                                    IconButton(
                                        onClick = {
                                            onEvent(
                                                ContactDetailsScreenEvents.OnCopyWalletClicked(
                                                    wallet = walletType
                                                )
                                            )
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_copy),
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = "Copiar"
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = dimens4)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(
                            topStart = dimens3,
                            topEnd = dimens3
                        )
                    )
            ) {

                Text(
                    text = string.commentTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimens4,
                            end = dimens4,
                            top = dimens4
                        ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                if (state.showLoadingComments) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimens2),
                        contentPadding = PaddingValues(dimens4),
                        content = {
                            items(4) { index ->
                                GenericShimmer(
                                    modifier = Modifier,
                                    cornerShape = dimens4,
                                    height = dimens20,
                                )
                            }
                        }
                    )

                } else {
                    if (state.comments.isEmpty()){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            Text(
                                text = string.emptyComments,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                                    .padding(
                                        start = dimens4,
                                        end = dimens4,
                                        top = dimens4
                                    ),
                                color = MaterialTheme.colorScheme.onSecondary,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        val listState = rememberLazyListState()
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            state = listState,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(dimens2),
                            contentPadding = PaddingValues(dimens4),
                            content = {
                                items(state.comments.size) { index ->
                                    val comment = state.comments[index]
                                    CardComments(
                                        commentsData = comment,
                                        string = string,
                                        onEvent = onEvent)
                                }
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun CardComments(
    commentsData: CommentsData,
    string: ContactDetailsScreenStrings,
    onEvent: (ContactDetailsScreenEvents) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(dimens4),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Text(
            text = commentsData.text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimens4,
                    end = dimens4,
                    top = dimens4,
                ),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimens2,
                    end = dimens4,
                    top = dimens4,
                    bottom = dimens4
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = string.date(commentsData.createdAt),
                modifier = Modifier
                    .wrapContentWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Thin
                ),
                textAlign = TextAlign.Start
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(
                onClick = {
                    onEvent(ContactDetailsScreenEvents
                        .OnLikeCommentClicked(commentsData.id)
                    )
                },
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.ic_favorite
                    ),
                    contentDescription = "Like",
                    tint = if (commentsData.isLiked)
                        MaterialTheme.colorScheme.tertiary
                    else MaterialTheme.colorScheme.scrim
                )
            }

            Text(
                text = string.likes(commentsData.likes),
                modifier = Modifier
                    .wrapContentWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Thin
                ),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun CustomOutlinedContainer(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.small
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun ContactDetailsScreenPreview() {
    AppTheme {
        ContactDetailsScreen(
            state = ContactDetailsScreenState(
                userData = UserData(
                    img = "",
                    username = "Koby.Harris95",
                    name = "Miss Sophia Williamson",
                    bitcoinWallet = "1sgQxKtQBtHc1wvEw42s9hcnz8VF9ee6",
                    ethereumWallet = "0x3103a5b4a0b1c51d2de4b8ebd13a3cfefdcd43aa",
                    litecoinWallet = "3tzpUeALpoPcpJFZ6CH3k37JtH7WS9eYY",
                    id = 1
                ),
                isLoadingUserData = true,
                showLoadingComments = true,
                showLoadingAddComments = false,
                comments = listOf(
                    CommentsData(
                        createdAt = "2025-04-23T03:05:25.745Z",
                        id = 1,
                        getUserId = 1,
                        text = "Vester vilitas decimus molestiae truculenter. " +
                                "Tametsi tracto vis damno coerceo volva statua. " +
                                "Ustilo amet dignissimos vulpes ventosus." +
                                "\nCalamitas dolor ademptio suppono auxilium tibi ubi acceptus. " +
                                "Tener bellum molestiae triduana delego suppono uxor conculco" +
                                " desparatus deprimo. Audentia acceptus itaque speciosus." +
                                "\nCuro vetus dens optio. Tactus damno suscipit arx appello " +
                                "adipiscor corpus careo vulgaris valde. Ventus tredecim " +
                                "cogito stella coerceo.",
                        likes = 8224,
                        isLiked = true
                    )
                ),
            ),
            onEvent = {},
            navigationEvents = MutableSharedFlow(),
            initViewModel = {},
            onBackPressed = {}
        )
    }
}
