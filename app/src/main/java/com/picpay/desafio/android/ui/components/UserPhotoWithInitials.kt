package com.picpay.desafio.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.picpay.desafio.android.commons.utils.getInitials

@Composable
fun UserPhotoWithInitials(
    modifier: Modifier,
    photoUrl: String?,
    userName: String,
    initialStyle: TextStyle,
) {

    val context = LocalContext.current
    var url by remember { mutableStateOf(photoUrl) }

    if (url != null) {
        SubcomposeAsyncImage(
            modifier = modifier
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(context)
                .data(url)
                .crossfade(true)
                .crossfade(300)
                .build(),
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(12.dp)
                            .height
                                (12.dp)
                            .align
                                (Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            contentDescription = null,
            onError = {
                url = null
            }
        )
    } else {
        val initials = userName.getInitials()

        Box(
            modifier =
                modifier
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape,
                    ).background(
                        color = MaterialTheme.colorScheme.primary,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = initials,
                color = MaterialTheme.colorScheme.onPrimary,
                style = initialStyle,
            )
        }
    }
}
