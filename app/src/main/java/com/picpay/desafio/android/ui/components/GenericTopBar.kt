package com.picpay.desafio.android.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.picpay.desafio.android.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopBar(
    modifier: Modifier = Modifier,
    startIcon: ImageVector? = null,
    title: String,
    endIcon: ImageVector? = null,
    onStartIconClick: (() -> Unit) = {},
    onEndIconClick: (() -> Unit) = {},
) {

    TopAppBar(
        modifier = modifier,
        title = { Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
        ) },
        navigationIcon = {
            startIcon?.let {
                IconButton(
                    onClick = { onStartIconClick() }
                ) {
                    Icon(
                        imageVector = it,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = ""
                    )
                }
            }
        },
        actions = {
            endIcon?.let {
                IconButton(
                    onClick = { onEndIconClick() }
                ) {
                    Icon(
                        imageVector = it,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = ""
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        )

    )
}

@Preview
@Composable
private fun GenericTopBarPreview() {
    AppTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            GenericTopBar(
                title = "Generic Top Bar",
                startIcon = Icons.AutoMirrored.Filled.ArrowBack,
                endIcon = null,
                onStartIconClick = { },
                onEndIconClick = { }
            )
        }
    }
}
